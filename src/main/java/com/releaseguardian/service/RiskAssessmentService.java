package com.releaseguardian.service;

import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentService {

    public RiskAssessmentResult assessRisk(
            int changedFiles,
            int impactedComponents) {

        int changedFilesScore = calculateChangedFilesScore(
                changedFiles
        );

        int impactedComponentsScore =
                calculateImpactedComponentsScore(
                        impactedComponents
                );

        int totalScore =
                changedFilesScore + impactedComponentsScore;

        String riskLevel = determineRiskLevel(totalScore);

        String riskReason =
                buildRiskReason(
                        changedFiles,
                        impactedComponents,
                        changedFilesScore,
                        impactedComponentsScore,
                        totalScore,
                        riskLevel
                );

        return new RiskAssessmentResult(
                totalScore,
                riskLevel,
                changedFilesScore,
                impactedComponentsScore,
                riskReason
        );
    }

    /*
     * Kept for compatibility with the existing workflow.
     *
     * Existing callers can still request only the risk level.
     */
    public String calculateRisk(
            int changedFiles,
            int impactedComponents) {

        return assessRisk(
                changedFiles,
                impactedComponents
        ).getRiskLevel();
    }

    private int calculateChangedFilesScore(
            int changedFiles) {

        // More changed files generally means greater release risk.
        if (changedFiles >= 20) {
            return 3;
        }

        if (changedFiles >= 10) {
            return 2;
        }

        if (changedFiles >= 5) {
            return 1;
        }

        return 0;
    }

    private int calculateImpactedComponentsScore(
            int impactedComponents) {

        // More impacted components increase the blast radius.
        if (impactedComponents >= 5) {
            return 3;
        }

        if (impactedComponents >= 3) {
            return 2;
        }

        if (impactedComponents >= 1) {
            return 1;
        }

        return 0;
    }

    private String determineRiskLevel(int score) {

        if (score >= 5) {
            return "HIGH";
        }

        if (score >= 3) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private String buildRiskReason(
            int changedFiles,
            int impactedComponents,
            int changedFilesScore,
            int impactedComponentsScore,
            int totalScore,
            String riskLevel) {

        return "Baseline risk calculated from "
                + changedFiles
                + " changed files ("
                + changedFilesScore
                + " points) and "
                + impactedComponents
                + " impacted components ("
                + impactedComponentsScore
                + " points). Total score: "
                + totalScore
                + ", resulting in "
                + riskLevel
                + " baseline risk.";
    }

    public static class RiskAssessmentResult {

        private final int riskScore;
        private final String riskLevel;
        private final int changedFilesScore;
        private final int impactedComponentsScore;
        private final String riskReason;

        public RiskAssessmentResult(
                int riskScore,
                String riskLevel,
                int changedFilesScore,
                int impactedComponentsScore,
                String riskReason) {

            this.riskScore = riskScore;
            this.riskLevel = riskLevel;
            this.changedFilesScore = changedFilesScore;
            this.impactedComponentsScore =
                    impactedComponentsScore;
            this.riskReason = riskReason;
        }

        public int getRiskScore() {
            return riskScore;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public int getChangedFilesScore() {
            return changedFilesScore;
        }

        public int getImpactedComponentsScore() {
            return impactedComponentsScore;
        }

        public String getRiskReason() {
            return riskReason;
        }
    }
}