package com.releaseguardian.controller;

import com.releaseguardian.model.ReleaseRequest;
import com.releaseguardian.model.ReleaseWorkflowResult;
import com.releaseguardian.orchestrator.ReleaseGuardianOrchestrator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/releases")
public class ReleaseController {

    private final ReleaseGuardianOrchestrator orchestrator;

    public ReleaseController(ReleaseGuardianOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping("/analyze")
    public ReleaseWorkflowResult analyze(
            @RequestBody ReleaseRequest request) {

        return orchestrator.execute(request);
    }
}