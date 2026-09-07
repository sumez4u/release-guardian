package com.releaseguardian.service;

import com.releaseguardian.model.FailureAnalysis;
import com.releaseguardian.model.ReleaseDecisionEvidence;
import com.releaseguardian.model.TestExecutionResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseDecisionEvidenceService {

    public ReleaseDecisionEvidence buildEvidence(
            String applicationName,
            String version,
            String commitId,
            int changedFiles,
            int impactedComponents,
            List<String> impactedComponentNames,
            String riskLevel,
            List<TestExecutionResult> executionResults,
            List<FailureAnalysis> failureAnalyses) {

        int totalTests = executionResults.size();

        int passedTests = (int) executionResults.stream()
                .filter(result ->
                        "PASSED".equals(result.getStatus()))
                .count();

        int failedTests = (int) executionResults.stream()
                .filter(result ->
                        "FAILED".equals(result.getStatus()))
                .count();

        int functionalFailures = (int) failureAnalyses.stream()
                .filter(analysis ->
                        "PRODUCT_DEFECT".equals(
                                analysis.getClassification()))
                .count();

        int activeFlakyFailures = (int) failureAnalyses.stream()
                .filter(analysis ->
                        "FLAKY_TEST".equals(
                                analysis.getClassification()))
                .count();

        int recoveredFlakyTests = (int) executionResults.stream()
                .filter(result ->
                        result.isRetryAttempted()
                                && "FAILED".equals(
                                result.getInitialStatus())
                                && "FLAKY".equals(
                                result.getInitialFailureType())
                                && "PASSED".equals(
                                result.getStatus()))
                .count();

        int retryAttempts = (int) executionResults.stream()
                .filter(TestExecutionResult::isRetryAttempted)
                .count();

        int flakyFailures =
                activeFlakyFailures + recoveredFlakyTests;

        boolean blockingFailure = failureAnalyses.stream()
                .anyMatch(analysis ->
                        "BLOCKING".equals(
                                analysis.getImpact()));

        return new ReleaseDecisionEvidence(
                applicationName,
                version,
                commitId,
                changedFiles,
                impactedComponents,
                impactedComponentNames,
                totalTests,
                passedTests,
                failedTests,
                functionalFailures,
                flakyFailures,
                recoveredFlakyTests,
                retryAttempts,
                riskLevel,
                blockingFailure
        );
    }
}