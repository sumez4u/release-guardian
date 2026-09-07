package com.releaseguardian.model;

public class EvidenceRecord {

    private String category;
    private String description;
    private String severity;
    private boolean blocking;

    public EvidenceRecord() {
    }

    public EvidenceRecord(
            String category,
            String description,
            String severity,
            boolean blocking) {

        this.category = category;
        this.description = description;
        this.severity = severity;
        this.blocking = blocking;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }
}