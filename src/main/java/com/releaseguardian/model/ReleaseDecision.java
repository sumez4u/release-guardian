package com.releaseguardian.model;

import java.util.List;

public class ReleaseDecision {

    private String recommendation;
    private String decision;
    private String confidence;
    private String reason;

    private List<String> blockingReasons;
    private List<String> supportingEvidence;
    private List<String> nextActions;

    public ReleaseDecision() {
    }

    public ReleaseDecision(
            String recommendation,
            String decision,
            String confidence,
            String reason,
            List<String> blockingReasons,
            List<String> supportingEvidence,
            List<String> nextActions) {

        this.recommendation = recommendation;
        this.decision = decision;
        this.confidence = confidence;
        this.reason = reason;
        this.blockingReasons = blockingReasons;
        this.supportingEvidence = supportingEvidence;
        this.nextActions = nextActions;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getBlockingReasons() {
        return blockingReasons;
    }

    public void setBlockingReasons(
            List<String> blockingReasons) {

        this.blockingReasons = blockingReasons;
    }

    public List<String> getSupportingEvidence() {
        return supportingEvidence;
    }

    public void setSupportingEvidence(
            List<String> supportingEvidence) {

        this.supportingEvidence = supportingEvidence;
    }

    public List<String> getNextActions() {
        return nextActions;
    }

    public void setNextActions(
            List<String> nextActions) {

        this.nextActions = nextActions;
    }
}