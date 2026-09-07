package com.releaseguardian.service;

import com.releaseguardian.model.TestCase;
import com.releaseguardian.model.TestExecutionResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestExecutionService {

    public List<TestExecutionResult> executeTests(
            List<TestCase> selectedTests,
            String scenario) {

        List<TestExecutionResult> results =
                new ArrayList<>();

        boolean cleanScenario =
                "CLEAN".equalsIgnoreCase(scenario);

        for (TestCase test : selectedTests) {

            /*
             * In the clean scenario all selected tests pass.
             */
            if (cleanScenario) {

                results.add(
                        new TestExecutionResult(
                                test.getTestId(),
                                "PASSED",
                                null,
                                "Test passed successfully"
                        )
                );

            } else if (test.getTestId().equals("TC-PAY-003")) {

                /*
                 * Default/DEFECT scenario deliberately contains
                 * a functional product defect.
                 */
                results.add(
                        new TestExecutionResult(
                                test.getTestId(),
                                "FAILED",
                                "FUNCTIONAL",
                                "Payment API returned HTTP 500"
                        )
                );

            } else if (test.getTestId().equals("TC-AUTH-002")) {

                /*
                 * First attempt fails because the test is known
                 * to be flaky.
                 */
                TestExecutionResult firstAttempt =
                        new TestExecutionResult(
                                test.getTestId(),
                                "FAILED",
                                "FLAKY",
                                "Known intermittent token timeout"
                        );

                /*
                 * Automatically retry the flaky test.
                 */
                TestExecutionResult retryResult =
                        retryTest(
                                test,
                                firstAttempt
                        );

                results.add(retryResult);

            } else {

                results.add(
                        new TestExecutionResult(
                                test.getTestId(),
                                "PASSED",
                                null,
                                "Test passed successfully"
                        )
                );
            }
        }

        return results;
    }

    private TestExecutionResult retryTest(
            TestCase test,
            TestExecutionResult firstAttempt) {

        TestExecutionResult retryResult =
                new TestExecutionResult(
                        test.getTestId(),
                        "PASSED",
                        null,
                        "Test passed successfully after automatic retry"
                );

        retryResult.setRetryAttempted(true);

        retryResult.setInitialStatus(
                firstAttempt.getStatus()
        );

        retryResult.setInitialFailureType(
                firstAttempt.getFailureType()
        );

        retryResult.setInitialMessage(
                firstAttempt.getMessage()
        );

        return retryResult;
    }
}