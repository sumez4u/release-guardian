package com.releaseguardian.model;

public class FailureAnalysis {

    private String testId;
    private String classification;
    private String severity;
    private String impact;
    private String recommendedAction;
    private String reasoning;

    public FailureAnalysis() {
    }

    public FailureAnalysis(
            String testId,
            String classification,
            String severity,
            String impact,
            String recommendedAction,
            String reasoning) {

        this.testId = testId;
        this.classification = classification;
        this.severity = severity;
        this.impact = impact;
        this.recommendedAction = recommendedAction;
        this.reasoning = reasoning;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}