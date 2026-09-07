package com.releaseguardian.service;

import com.releaseguardian.model.ReleaseEvidence;
import com.releaseguardian.model.ReleaseRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseEvidenceService {

    private final ComponentImpactService componentImpactService;

    public ReleaseEvidenceService(
            ComponentImpactService componentImpactService) {

        this.componentImpactService = componentImpactService;
    }

    public ReleaseEvidence collectEvidence(ReleaseRequest request) {

        List<String> changedFiles;

        /*
         * CLEAN scenario represents a release with a small,
         * low-risk change set.
         */
        if ("CLEAN".equalsIgnoreCase(request.getScenario())) {

            changedFiles = List.of(
                    "src/main/java/payment/PaymentValidator.java"
            );

        } else {

            /*
             * DEFECT scenario keeps the original release evidence
             * so we can demonstrate a blocking production defect.
             */
            changedFiles = List.of(
                    "src/main/java/payment/PaymentController.java",
                    "src/main/java/payment/PaymentService.java",
                    "src/main/java/payment/PaymentRepository.java",
                    "src/main/java/auth/TokenService.java",
                    "src/main/java/payment/PaymentValidator.java",
                    "src/test/java/payment/PaymentServiceTest.java"
            );
        }

        List<String> impactedComponents =
                componentImpactService.identifyImpactedComponents(
                        changedFiles
                );

        return new ReleaseEvidence(
                changedFiles.size(),
                changedFiles,
                impactedComponents
        );
    }
}