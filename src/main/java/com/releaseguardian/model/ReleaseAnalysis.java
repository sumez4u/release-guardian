package com.releaseguardian.model;

import com.releaseguardian.service.RiskAssessmentService;

import java.util.List;

public class ReleaseAnalysis {

    private String applicationName;
    private String version;
    private String riskLevel;

    private int changedFiles;
    private int impactedComponents;
    private List<String> impactedComponentNames;

    private RiskAssessmentService.RiskAssessmentResult riskAssessment;

    private List<TestCase> selectedTests;
    private List<TestExecutionResult> executionResults;
    private List<FailureAnalysis> failureAnalyses;
    private ReleaseDecisionEvidence decisionEvidence;

    private ReleaseDecision releaseDecision;

    private String recommendation;

    public ReleaseAnalysis() {
    }

    public ReleaseAnalysis(
            String applicationName,
            String version,
            String riskLevel,
            int changedFiles,
            int impactedComponents,
            List<String> impactedComponentNames,
            RiskAssessmentService.RiskAssessmentResult riskAssessment,
            List<TestCase> selectedTests,
            List<TestExecutionResult> executionResults,
            List<FailureAnalysis> failureAnalyses,
            ReleaseDecisionEvidence decisionEvidence,
            ReleaseDecision releaseDecision,
            String recommendation) {

        this.applicationName = applicationName;
        this.version = version;
        this.riskLevel = riskLevel;
        this.changedFiles = changedFiles;
        this.impactedComponents = impactedComponents;
        this.impactedComponentNames = impactedComponentNames;
        this.riskAssessment = riskAssessment;
        this.selectedTests = selectedTests;
        this.executionResults = executionResults;
        this.failureAnalyses = failureAnalyses;
        this.decisionEvidence = decisionEvidence;
        this.releaseDecision = releaseDecision;
        this.recommendation = recommendation;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
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

    public RiskAssessmentService.RiskAssessmentResult getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(
            RiskAssessmentService.RiskAssessmentResult riskAssessment) {

        this.riskAssessment = riskAssessment;
    }

    public List<TestCase> getSelectedTests() {
        return selectedTests;
    }

    public void setSelectedTests(List<TestCase> selectedTests) {
        this.selectedTests = selectedTests;
    }

    public List<TestExecutionResult> getExecutionResults() {
        return executionResults;
    }

    public void setExecutionResults(
            List<TestExecutionResult> executionResults) {

        this.executionResults = executionResults;
    }

    public List<FailureAnalysis> getFailureAnalyses() {
        return failureAnalyses;
    }

    public void setFailureAnalyses(
            List<FailureAnalysis> failureAnalyses) {

        this.failureAnalyses = failureAnalyses;
    }

    public ReleaseDecisionEvidence getDecisionEvidence() {
        return decisionEvidence;
    }

    public void setDecisionEvidence(
            ReleaseDecisionEvidence decisionEvidence) {

        this.decisionEvidence = decisionEvidence;
    }

    public ReleaseDecision getReleaseDecision() {
        return releaseDecision;
    }

    public void setReleaseDecision(
            ReleaseDecision releaseDecision) {

        this.releaseDecision = releaseDecision;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}