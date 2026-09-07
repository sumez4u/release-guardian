package com.releaseguardian.service;

import com.releaseguardian.model.FailureAnalysis;
import com.releaseguardian.model.TestExecutionResult;
import org.springframework.stereotype.Service;

@Service
public class FailureAnalysisService {

    public FailureAnalysis analyze(
            TestExecutionResult result) {

        if ("PASSED".equals(result.getStatus())) {

            return new FailureAnalysis(
                    result.getTestId(),
                    "NO_FAILURE",
                    "NONE",
                    "NONE",
                    "CONTINUE",
                    "Test passed successfully."
            );
        }

        if ("FLAKY".equals(result.getFailureType())) {

            return new FailureAnalysis(
                    result.getTestId(),
                    "FLAKY_TEST",
                    "MEDIUM",
                    "NON_BLOCKING",
                    "RETRY",
                    "The test failure is classified as flaky and should be retried before blocking the release."
            );
        }

        if ("FUNCTIONAL".equals(result.getFailureType())) {

            return new FailureAnalysis(
                    result.getTestId(),
                    "PRODUCT_DEFECT",
                    "CRITICAL",
                    "BLOCKING",
                    "HOLD",
                    "A functional test failure indicates a potential product defect and should block the release until investigated."
            );
        }

        return new FailureAnalysis(
                result.getTestId(),
                "UNKNOWN_FAILURE",
                "HIGH",
                "REVIEW_REQUIRED",
                "INVESTIGATE",
                "The failure could not be confidently classified and requires investigation."
        );
    }
}