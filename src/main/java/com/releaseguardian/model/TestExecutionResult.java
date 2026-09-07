package com.releaseguardian.model;

public class TestExecutionResult {

    private String testId;
    private String status;
    private String failureType;
    private String message;

    private boolean retryAttempted;
    private String initialStatus;
    private String initialFailureType;
    private String initialMessage;

    public TestExecutionResult() {
    }

    public TestExecutionResult(
            String testId,
            String status,
            String failureType,
            String message) {

        this.testId = testId;
        this.status = status;
        this.failureType = failureType;
        this.message = message;
        this.retryAttempted = false;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureType() {
        return failureType;
    }

    public void setFailureType(String failureType) {
        this.failureType = failureType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRetryAttempted() {
        return retryAttempted;
    }

    public void setRetryAttempted(boolean retryAttempted) {
        this.retryAttempted = retryAttempted;
    }

    public String getInitialStatus() {
        return initialStatus;
    }

    public void setInitialStatus(String initialStatus) {
        this.initialStatus = initialStatus;
    }

    public String getInitialFailureType() {
        return initialFailureType;
    }

    public void setInitialFailureType(String initialFailureType) {
        this.initialFailureType = initialFailureType;
    }

    public String getInitialMessage() {
        return initialMessage;
    }

    public void setInitialMessage(String initialMessage) {
        this.initialMessage = initialMessage;
    }
}