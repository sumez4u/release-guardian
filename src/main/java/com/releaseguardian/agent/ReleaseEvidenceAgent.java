package com.releaseguardian.agent;

import com.releaseguardian.model.EvidenceRecord;
import com.releaseguardian.model.ReleaseAnalysis;
import com.releaseguardian.model.ReleaseRequest;
import com.releaseguardian.model.TestCase;
import com.releaseguardian.model.TestExecutionResult;
import com.releaseguardian.service.ReleaseAnalysisService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReleaseEvidenceAgent {

    private final ReleaseAnalysisService releaseAnalysisService;

    public ReleaseEvidenceAgent(
            ReleaseAnalysisService releaseAnalysisService) {

        this.releaseAnalysisService = releaseAnalysisService;
    }

    public EvidenceResult collectEvidence(
            ReleaseRequest request) {

        ReleaseAnalysis analysis =
                releaseAnalysisService.analyze(request);

        List<EvidenceRecord> evidence =
                new ArrayList<>();

        if (analysis.getFailureAnalyses() != null) {

            analysis.getFailureAnalyses().forEach(failure -> {

                TestCase test = analysis.getSelectedTests()
                        .stream()
                        .filter(t ->
                                t.getTestId().equals(
                                        failure.getTestId()))
                        .findFirst()
                        .orElse(null);

                String testName =
                        test != null
                                ? test.getTestName()
                                : "Unknown test";

                TestExecutionResult executionResult =
                        analysis.getExecutionResults()
                                .stream()
                                .filter(r ->
                                        r.getTestId().equals(
                                                failure.getTestId()))
                                .findFirst()
                                .orElse(null);

                String failureMessage =
                        executionResult != null
                                ? executionResult.getMessage()
                                : "Test failure detected.";

                if ("PRODUCT_DEFECT".equals(
                        failure.getClassification())) {

                    evidence.add(
                            new EvidenceRecord(
                                    "PRODUCT_DEFECT",
                                    failure.getTestId()
                                            + " ("
                                            + testName
                                            + ") failed because "
                                            + failureMessage
                                            + ".",
                                    "CRITICAL",
                                    true
                            )
                    );
                }
            });
        }

        /*
         * Preserve recovered flaky-test evidence.
         *
         * A flaky test that passes after retry should not block
         * the release, but its initial failure must remain visible
         * for auditability and decision transparency.
         */
        if (analysis.getExecutionResults() != null) {

            analysis.getExecutionResults().forEach(result -> {

                if (result.isRetryAttempted()
                        && "FAILED".equals(
                        result.getInitialStatus())
                        && "FLAKY".equals(
                        result.getInitialFailureType())
                        && "PASSED".equals(
                        result.getStatus())) {

                    TestCase test = analysis.getSelectedTests()
                            .stream()
                            .filter(t ->
                                    t.getTestId().equals(
                                            result.getTestId()))
                            .findFirst()
                            .orElse(null);

                    String testName =
                            test != null
                                    ? test.getTestName()
                                    : "Unknown test";

                    evidence.add(
                            new EvidenceRecord(
                                    "RECOVERED_FLAKY_TEST",
                                    result.getTestId()
                                            + " ("
                                            + testName
                                            + ") initially failed due to "
                                            + result.getInitialMessage()
                                            + " but passed after automatic retry.",
                                    "MEDIUM",
                                    false
                            )
                    );
                }
            });
        }

        return new EvidenceResult(
                analysis,
                evidence
        );
    }

    public static class EvidenceResult {

        private final ReleaseAnalysis analysis;
        private final List<EvidenceRecord> evidence;

        public EvidenceResult(
                ReleaseAnalysis analysis,
                List<EvidenceRecord> evidence) {

            this.analysis = analysis;
            this.evidence = evidence;
        }

        public ReleaseAnalysis getAnalysis() {
            return analysis;
        }

        public List<EvidenceRecord> getEvidence() {
            return evidence;
        }
    }
}