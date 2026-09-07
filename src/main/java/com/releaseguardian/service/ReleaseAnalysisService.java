package com.releaseguardian.service;

import com.releaseguardian.model.FailureAnalysis;
import com.releaseguardian.model.ReleaseAnalysis;
import com.releaseguardian.model.ReleaseDecision;
import com.releaseguardian.model.ReleaseDecisionEvidence;
import com.releaseguardian.model.ReleaseEvidence;
import com.releaseguardian.model.ReleaseRequest;
import com.releaseguardian.model.TestCase;
import com.releaseguardian.model.TestExecutionResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseAnalysisService {

    private final RiskAssessmentService riskAssessmentService;
    private final ReleaseEvidenceService releaseEvidenceService;
    private final TestSelectionService testSelectionService;
    private final TestExecutionService testExecutionService;
    private final FailureAnalysisService failureAnalysisService;
    private final ReleaseDecisionEvidenceService releaseDecisionEvidenceService;
    private final ReleaseDecisionService releaseDecisionService;

    public ReleaseAnalysisService(
            RiskAssessmentService riskAssessmentService,
            ReleaseEvidenceService releaseEvidenceService,
            TestSelectionService testSelectionService,
            TestExecutionService testExecutionService,
            FailureAnalysisService failureAnalysisService,
            ReleaseDecisionEvidenceService releaseDecisionEvidenceService,
            ReleaseDecisionService releaseDecisionService) {

        this.riskAssessmentService = riskAssessmentService;
        this.releaseEvidenceService = releaseEvidenceService;
        this.testSelectionService = testSelectionService;
        this.testExecutionService = testExecutionService;
        this.failureAnalysisService = failureAnalysisService;
        this.releaseDecisionEvidenceService =
                releaseDecisionEvidenceService;
        this.releaseDecisionService =
                releaseDecisionService;
    }

    public ReleaseAnalysis analyze(ReleaseRequest request) {

        // Step 1: Collect release evidence
        ReleaseEvidence evidence =
                releaseEvidenceService.collectEvidence(request);

        int changedFiles =
                evidence.getChangedFiles();

        List<String> impactedComponents =
                evidence.getImpactedComponents();

        int impactedComponentCount =
                impactedComponents.size();

        // Step 2: Calculate transparent baseline risk
        RiskAssessmentService.RiskAssessmentResult riskAssessment =
                riskAssessmentService.assessRisk(
                        changedFiles,
                        impactedComponentCount
                );

        String riskLevel =
                riskAssessment.getRiskLevel();

        // Step 3: Select relevant tests
        List<TestCase> selectedTests =
                testSelectionService.selectTests(
                        impactedComponents
                );

        // Step 4: Execute selected tests
        List<TestExecutionResult> executionResults =
                testExecutionService.executeTests(
                        selectedTests,
                        request.getScenario()
                );

        List<FailureAnalysis> failureAnalyses =
                executionResults.stream()
                        .map(failureAnalysisService::analyze)
                        .toList();

        // Step 5: Escalate risk based on actual test failures
        boolean functionalFailureFound =
                executionResults.stream()
                        .anyMatch(result ->
                                "FAILED".equals(result.getStatus())
                                        && "FUNCTIONAL".equals(
                                        result.getFailureType()
                                )
                        );

        boolean flakyFailureFound =
                executionResults.stream()
                        .anyMatch(result ->
                                "FAILED".equals(result.getStatus())
                                        && "FLAKY".equals(
                                        result.getFailureType()
                                )
                        );

        if (functionalFailureFound) {

            riskLevel = "CRITICAL";

        } else if (flakyFailureFound
                && "LOW".equals(riskLevel)) {

            riskLevel = "MEDIUM";
        }

        // Step 6: Build final decision evidence
        ReleaseDecisionEvidence decisionEvidence =
                releaseDecisionEvidenceService.buildEvidence(
                        request.getApplicationName(),
                        request.getVersion(),
                        request.getCommitId(),
                        changedFiles,
                        impactedComponentCount,
                        impactedComponents,
                        riskLevel,
                        executionResults,
                        failureAnalyses
                );

        // Step 7: Determine release recommendation
        String recommendation =
                determineRecommendation(
                        executionResults,
                        riskLevel
                );

        // Step 8: Build explainable release decision
        ReleaseDecision releaseDecision =
                releaseDecisionService.makeDecision(
                        recommendation,
                        riskLevel,
                        executionResults.size(),
                        (int) executionResults.stream()
                                .filter(result ->
                                        "PASSED".equals(
                                                result.getStatus()))
                                .count(),
                        (int) executionResults.stream()
                                .filter(result ->
                                        "FAILED".equals(
                                                result.getStatus()))
                                .count(),
                        executionResults,
                        failureAnalyses
                );

        return new ReleaseAnalysis(
                request.getApplicationName(),
                request.getVersion(),
                riskLevel,
                changedFiles,
                impactedComponentCount,
                impactedComponents,
                riskAssessment,
                selectedTests,
                executionResults,
                failureAnalyses,
                decisionEvidence,
                releaseDecision,
                recommendation
        );
    }

    private String determineRecommendation(
            List<TestExecutionResult> results,
            String riskLevel) {

        boolean functionalFailureFound =
                results.stream()
                        .anyMatch(result ->
                                "FAILED".equals(result.getStatus())
                                        && "FUNCTIONAL".equals(
                                        result.getFailureType()
                                )
                        );

        if (functionalFailureFound) {
            return "HOLD";
        }

        if ("HIGH".equals(riskLevel)
                || "CRITICAL".equals(riskLevel)) {

            return "HOLD";
        }

        return "RELEASE";
    }
}