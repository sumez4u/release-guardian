package com.releaseguardian.model;

public class TestCase {

    private String testId;
    private String testName;
    private String component;
    private String priority;

    private int selectionScore;
    private String selectionReason;

    public TestCase() {
    }

    public TestCase(
            String testId,
            String testName,
            String component,
            String priority) {

        this.testId = testId;
        this.testName = testName;
        this.component = component;
        this.priority = priority;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getSelectionScore() {
        return selectionScore;
    }

    public void setSelectionScore(int selectionScore) {
        this.selectionScore = selectionScore;
    }

    public String getSelectionReason() {
        return selectionReason;
    }

    public void setSelectionReason(String selectionReason) {
        this.selectionReason = selectionReason;
    }
}