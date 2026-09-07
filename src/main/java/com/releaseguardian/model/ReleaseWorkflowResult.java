package com.releaseguardian.model;
import java.util.List;

public class ReleaseWorkflowResult {

    private String status;
    private String message;
    private ReleaseAnalysis releaseAnalysis;
    private List<EvidenceRecord> evidence;

    public ReleaseWorkflowResult() {
    }

    public ReleaseWorkflowResult(
        String status,
        String message,
        ReleaseAnalysis releaseAnalysis,
        List<EvidenceRecord> evidence) {

    this.status = status;
    this.message = message;
    this.releaseAnalysis = releaseAnalysis;
    this.evidence = evidence;
    }

    public List<EvidenceRecord> getEvidence() {
    return evidence;
    }

    public void setEvidence(List<EvidenceRecord> evidence) {
    this.evidence = evidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReleaseAnalysis getReleaseAnalysis() {
        return releaseAnalysis;
    }

    public void setReleaseAnalysis(
            ReleaseAnalysis releaseAnalysis) {

        this.releaseAnalysis = releaseAnalysis;
    }
}