package com.releaseguardian.service;

import com.releaseguardian.model.FailureAnalysis;
import com.releaseguardian.model.ReleaseDecision;
import com.releaseguardian.model.TestExecutionResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleaseDecisionService {

    public ReleaseDecision makeDecision(
            String recommendation,
            String riskLevel,
            int totalTests,
            int passedTests,
            int failedTests,
            List<TestExecutionResult> executionResults,
            List<FailureAnalysis> failureAnalyses) {

        List<String> blockingReasons = new ArrayList<>();
        List<String> supportingEvidence = new ArrayList<>();
        List<String> nextActions = new ArrayList<>();

        boolean blockingFailureFound =
                failureAnalyses.stream()
                        .anyMatch(analysis ->
                                "BLOCKING".equals(
                                        analysis.getImpact()));

        boolean functionalFailureFound =
                failureAnalyses.stream()
                        .anyMatch(analysis ->
                                "PRODUCT_DEFECT".equals(
                                        analysis.getClassification()));

        int recoveredFlakyTests =
                (int) executionResults.stream()
                        .filter(result ->
                                result.isRetryAttempted()
                                        && "FAILED".equals(
                                        result.getInitialStatus())
                                        && "FLAKY".equals(
                                        result.getInitialFailureType())
                                        && "PASSED".equals(
                                        result.getStatus()))
                        .count();

        if (blockingFailureFound) {

            failureAnalyses.stream()
                    .filter(analysis ->
                            "BLOCKING".equals(
                                    analysis.getImpact()))
                    .forEach(analysis ->
                            blockingReasons.add(
                                    analysis.getTestId()
                                            + " was classified as "
                                            + analysis.getClassification()
                                            + " with BLOCKING impact."
                            )
                    );

            if (functionalFailureFound) {
                nextActions.add(
                        "Investigate the failing functional test and associated product defect."
                );
            }

            nextActions.add(
                    "Fix the blocking issue and rerun the impacted regression tests."
            );

            nextActions.add(
                    "Re-evaluate the release after the blocking failure is resolved."
            );
        }

        supportingEvidence.add(
                passedTests
                        + " of "
                        + totalTests
                        + " selected tests passed."
        );

        if (recoveredFlakyTests > 0) {
            supportingEvidence.add(
                    recoveredFlakyTests
                            + " flaky test(s) recovered successfully after automatic retry."
            );
        }

        supportingEvidence.add(
                "Final risk level is "
                        + riskLevel
                        + " after incorporating test execution evidence."
        );

        String decision;
        String confidence;
        String reason;

        if ("HOLD".equals(recommendation)) {

            decision = "BLOCK_RELEASE";

            if (blockingFailureFound) {
                confidence = "HIGH";
                reason =
                        "Release is blocked because a blocking test failure was detected during validation.";
            } else {
                confidence = "MEDIUM";
                reason =
                        "Release is being held because the calculated release risk is too high.";
            }

        } else {

            decision = "APPROVE_RELEASE";
            confidence = "HIGH";
            reason =
                    "Release is approved because no blocking validation failures were detected.";
        }

        return new ReleaseDecision(
                recommendation,
                decision,
                confidence,
                reason,
                blockingReasons,
                supportingEvidence,
                nextActions
        );
    }
}