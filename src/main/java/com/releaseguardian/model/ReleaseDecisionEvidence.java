package com.releaseguardian.model;

import java.util.List;

public class ReleaseDecisionEvidence {

    private String applicationName;
    private String version;
    private String commitId;

    private int changedFiles;
    private int impactedComponents;

    private List<String> impactedComponentNames;

    private int totalTests;
    private int passedTests;
    private int failedTests;

    private int functionalFailures;
    private int flakyFailures;
    private int recoveredFlakyTests;
    private int retryAttempts;

    private String riskLevel;

    private boolean blockingFailure;

    public ReleaseDecisionEvidence() {
    }

    public ReleaseDecisionEvidence(
            String applicationName,
            String version,
            String commitId,
            int changedFiles,
            int impactedComponents,
            List<String> impactedComponentNames,
            int totalTests,
            int passedTests,
            int failedTests,
            int functionalFailures,
            int flakyFailures,
            int recoveredFlakyTests,
            int retryAttempts,
            String riskLevel,
            boolean blockingFailure) {

        this.applicationName = applicationName;
        this.version = version;
        this.commitId = commitId;
        this.changedFiles = changedFiles;
        this.impactedComponents = impactedComponents;
        this.impactedComponentNames = impactedComponentNames;
        this.totalTests = totalTests;
        this.passedTests = passedTests;
        this.failedTests = failedTests;
        this.functionalFailures = functionalFailures;
        this.flakyFailures = flakyFailures;
        this.recoveredFlakyTests = recoveredFlakyTests;
        this.retryAttempts = retryAttempts;
        this.riskLevel = riskLevel;
        this.blockingFailure = blockingFailure;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public int getChangedFiles() {
        return changedFiles;
    }

    public void setChangedFiles(int changedFiles) {
        this.changedFiles = changedFiles;
    }

    public int getImpactedComponents() {
        return impactedComponents;
    }

    public void setImpactedComponents(int impactedComponents) {
        this.impactedComponents = impactedComponents;
    }

    public List<String> getImpactedComponentNames() {
        return impactedComponentNames;
    }

    public void setImpactedComponentNames(
            List<String> impactedComponentNames) {

        this.impactedComponentNames = impactedComponentNames;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public void setFailedTests(int failedTests) {
        this.failedTests = failedTests;
    }

    public int getFunctionalFailures() {
        return functionalFailures;
    }

    public void setFunctionalFailures(int functionalFailures) {
        this.functionalFailures = functionalFailures;
    }

    public int getFlakyFailures() {
        return flakyFailures;
    }

    public void setFlakyFailures(int flakyFailures) {
        this.flakyFailures = flakyFailures;
    }

    public int getRecoveredFlakyTests() {
        return recoveredFlakyTests;
    }

    public void setRecoveredFlakyTests(int recoveredFlakyTests) {
        this.recoveredFlakyTests = recoveredFlakyTests;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public boolean isBlockingFailure() {
        return blockingFailure;
    }

    public void setBlockingFailure(boolean blockingFailure) {
        this.blockingFailure = blockingFailure;
    }
}