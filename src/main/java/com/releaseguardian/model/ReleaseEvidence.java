package com.releaseguardian.model;

import java.util.List;

public class ReleaseEvidence {

    private int changedFiles;
    private List<String> changedFilePaths;
    private List<String> impactedComponents;

    public ReleaseEvidence() {
    }

    public ReleaseEvidence(
            int changedFiles,
            List<String> changedFilePaths,
            List<String> impactedComponents) {

        this.changedFiles = changedFiles;
        this.changedFilePaths = changedFilePaths;
        this.impactedComponents = impactedComponents;
    }

    public int getChangedFiles() {
        return changedFiles;
    }

    public void setChangedFiles(int changedFiles) {
        this.changedFiles = changedFiles;
    }

    public List<String> getChangedFilePaths() {
        return changedFilePaths;
    }

    public void setChangedFilePaths(List<String> changedFilePaths) {
        this.changedFilePaths = changedFilePaths;
    }

    public List<String> getImpactedComponents() {
        return impactedComponents;
    }

    public void setImpactedComponents(List<String> impactedComponents) {
        this.impactedComponents = impactedComponents;
    }
}