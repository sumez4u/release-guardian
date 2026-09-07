package com.releaseguardian.orchestrator;

import com.releaseguardian.agent.ReleaseEvidenceAgent;
import com.releaseguardian.model.ReleaseAnalysis;
import com.releaseguardian.model.ReleaseRequest;
import com.releaseguardian.model.ReleaseWorkflowResult;
import org.springframework.stereotype.Service;

@Service
public class ReleaseGuardianOrchestrator {

    private final ReleaseEvidenceAgent releaseEvidenceAgent;

    public ReleaseGuardianOrchestrator(
            ReleaseEvidenceAgent releaseEvidenceAgent) {

        this.releaseEvidenceAgent = releaseEvidenceAgent;
    }

    public ReleaseWorkflowResult execute(
            ReleaseRequest request) {

        ReleaseEvidenceAgent.EvidenceResult evidenceResult =
            releaseEvidenceAgent.collectEvidence(request);

        ReleaseAnalysis analysis =
            evidenceResult.getAnalysis();

        return new ReleaseWorkflowResult(
        "COMPLETED",
        "Release analysis workflow completed",
        analysis,
        evidenceResult.getEvidence()
        );
    }
}