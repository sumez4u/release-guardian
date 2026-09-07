package com.releaseguardian.service;

import com.releaseguardian.model.TestCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TestSelectionService {

    public List<TestCase> selectTests(
            List<String> impactedComponents) {

        List<TestCase> testCatalog = createTestCatalog();

        List<TestCase> selectedTests = new ArrayList<>();

        for (TestCase test : testCatalog) {

            if (impactedComponents.contains(test.getComponent())) {

                enrichSelectionDecision(test);

                selectedTests.add(test);
            }
        }

        /*
         * Execute higher-value tests first.
         *
         * This makes the selection process more useful during
         * a time-constrained release validation.
         */
        selectedTests.sort(
                Comparator.comparingInt(
                        TestCase::getSelectionScore
                ).reversed()
        );

        return selectedTests;
    }

    private void enrichSelectionDecision(TestCase test) {

        int score = calculateSelectionScore(
                test.getPriority()
        );

        test.setSelectionScore(score);

        test.setSelectionReason(
                "Selected because "
                        + test.getComponent()
                        + " is an impacted component and this test has "
                        + test.getPriority()
                        + " priority."
        );
    }

    private int calculateSelectionScore(String priority) {

        if ("HIGH".equals(priority)) {
            return 90;
        }

        if ("MEDIUM".equals(priority)) {
            return 60;
        }

        if ("LOW".equals(priority)) {
            return 30;
        }

        return 10;
    }

    private List<TestCase> createTestCatalog() {

        return List.of(

                new TestCase(
                        "TC-PAY-001",
                        "Create payment",
                        "PAYMENT",
                        "HIGH"
                ),

                new TestCase(
                        "TC-PAY-002",
                        "Validate payment amount",
                        "PAYMENT",
                        "HIGH"
                ),

                new TestCase(
                        "TC-PAY-003",
                        "Payment API regression",
                        "PAYMENT",
                        "MEDIUM"
                ),

                new TestCase(
                        "TC-AUTH-001",
                        "User login",
                        "AUTHENTICATION",
                        "HIGH"
                ),

                new TestCase(
                        "TC-AUTH-002",
                        "Token validation",
                        "AUTHENTICATION",
                        "HIGH"
                ),

                new TestCase(
                        "TC-AUTH-003",
                        "Authorization validation",
                        "AUTHENTICATION",
                        "MEDIUM"
                )
        );
    }
}