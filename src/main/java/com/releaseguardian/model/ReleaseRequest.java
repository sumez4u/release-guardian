package com.releaseguardian.model;

public class ReleaseRequest {

    private String applicationName;
    private String version;
    private String commitId;
    private String scenario;

    public ReleaseRequest() {
    }

    public ReleaseRequest(
            String applicationName,
            String version,
            String commitId,
            String scenario) {

        this.applicationName = applicationName;
        this.version = version;
        this.commitId = commitId;
        this.scenario = scenario;
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

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}