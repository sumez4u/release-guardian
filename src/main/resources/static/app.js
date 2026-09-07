async function analyzeRelease() {

    const applicationName =
        document.getElementById("applicationName").value;

    const version =
        document.getElementById("version").value;

    const commitId =
        document.getElementById("commitId").value;

    const scenario =
        document.getElementById("scenario").value;

    const button =
        document.getElementById("analyzeButton");

    const loading =
        document.getElementById("loading");

    button.disabled = true;
    loading.classList.remove("hidden");

    try {

        const response = await fetch(
            "/api/releases/analyze",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    applicationName: applicationName,
                    version: version,
                    commitId: commitId,
                    scenario: scenario
                })
            }
        );

        if (!response.ok) {
            throw new Error(
                "API request failed: HTTP " + response.status
            );
        }

        const data = await response.json();

        window.releaseEvidence = data.evidence || [];

        displayReleaseAnalysis(
            data.releaseAnalysis
        );

    } catch (error) {

        console.error(error);

        alert(
            "Release analysis failed: " +
            error.message
        );

    } finally {

        button.disabled = false;
        loading.classList.add("hidden");
    }
}


function displayReleaseAnalysis(analysis) {

    if (!analysis) {
        return;
    }

    /*
     * Release decision
     */
    const decision =
        analysis.releaseDecision;

    const decisionBanner =
        document.getElementById("decisionBanner");

    decisionBanner.classList.remove("hidden");

    document.getElementById("decisionText")
        .textContent =
        formatLabel(decision.decision);

    document.getElementById("decisionReason")
        .textContent =
        decision.reason;

    document.getElementById("confidenceBadge")
        .textContent =
        formatLabel(decision.confidence) + " CONFIDENCE";


    /*
     * Metrics
     */
    document.getElementById("riskLevel")
        .textContent =
        formatLabel(analysis.riskLevel);

    document.getElementById("changedFiles")
        .textContent =
        analysis.changedFiles;

    document.getElementById("impactedComponents")
        .textContent =
        analysis.impactedComponents;

    const executionResults =
        analysis.executionResults || [];

    const passedTests =
        executionResults.filter(
            test => test.status === "PASSED"
        ).length;

    const failedTests =
        executionResults.filter(
            test => test.status === "FAILED"
        ).length;

    document.getElementById("totalTests")
        .textContent =
        executionResults.length;

    document.getElementById("passedTests")
        .textContent =
        passedTests;

    document.getElementById("failedTests")
        .textContent =
        failedTests;


    /*
     * Impacted components
     */
    const components =
        document.getElementById("components");

    components.innerHTML = "";

    (analysis.impactedComponentNames || [])
        .forEach(component => {

            const tag =
                document.createElement("span");

            tag.className = "tag";
            tag.textContent = formatLabel(component);

            components.appendChild(tag);
        });


    /*
     * Risk assessment
     */
    const riskAssessment =
        analysis.riskAssessment;

    if (riskAssessment) {

        document.getElementById("riskScore")
            .textContent =
            riskAssessment.riskScore;

        document.getElementById("riskReason")
            .textContent =
            riskAssessment.riskReason;
    }


    /*
     * Evidence
     */
    const evidenceList =
        document.getElementById("evidenceList");

    evidenceList.innerHTML = "";

    (window.releaseEvidence || [])
        .forEach(evidence => {

            const item =
                document.createElement("div");

            item.className =
                "evidence-item";

            item.innerHTML = `
                <strong>
                    ${formatEvidenceCategory(evidence.category)}
                </strong>

                <span>
                    ${evidence.description}
                    • Severity: ${formatLabel(evidence.severity)}
                    • Blocking: ${formatBoolean(evidence.blocking)}
                </span>
            `;

            evidenceList.appendChild(item);
        });


    /*
     * Selected tests
     */
    const testList =
        document.getElementById("testList");

    testList.innerHTML = "";

    (analysis.selectedTests || [])
        .forEach(test => {

            const item =
                document.createElement("div");

            item.className =
                "test-item";

            item.innerHTML = `
                <div class="test-header">

                    <span class="test-id">
                        ${test.testId}
                    </span>

                    <span class="priority">
                        ${formatLabel(test.priority)}
                    </span>

                </div>

                <div class="test-name">
                    ${test.testName}
                </div>

                <div class="test-reason">
                    ${test.selectionReason}
                </div>
            `;

            testList.appendChild(item);
        });


    /*
     * Decision explanation
     */
    populateList(
        "blockingReasons",
        decision.blockingReasons
    );

    populateList(
        "supportingEvidence",
        decision.supportingEvidence
    );

    populateList(
        "nextActions",
        decision.nextActions
    );
}


function populateList(
    elementId,
    items
) {

    const list =
        document.getElementById(elementId);

    list.innerHTML = "";

    if (!items || items.length === 0) {

        const item =
            document.createElement("li");

        item.textContent =
            "None";

        list.appendChild(item);

        return;
    }

    items.forEach(text => {

        const item =
            document.createElement("li");

        item.textContent =
            formatDecisionText(text);

        list.appendChild(item);
    });
}


/*
 * Convert technical enum-style values
 * into human-readable dashboard labels.
 */
function formatLabel(value) {

    if (value === null || value === undefined) {
        return "";
    }

    return String(value)
        .replace(/_/g, " ")
        .toLowerCase()
        .replace(/\b\w/g, char => char.toUpperCase());
}


/*
 * Evidence category formatting
 */
function formatEvidenceCategory(category) {

    const labels = {
        "PRODUCT_DEFECT": "Product Defect",
        "RECOVERED_FLAKY_TEST": "Recovered Flaky Test"
    };

    return labels[category] || formatLabel(category);
}


/*
 * Boolean formatting
 */
function formatBoolean(value) {

    return value ? "Yes" : "No";
}


/*
 * Improve readability of decision explanation text
 * while preserving the original meaning.
 */
function formatDecisionText(text) {

    if (!text) {
        return "";
    }

    return String(text)
        .replace(/PRODUCT_DEFECT/g, "Product Defect")
        .replace(/RECOVERED_FLAKY_TEST/g, "Recovered Flaky Test")
        .replace(/BLOCKING/g, "Blocking")
        .replace(/NON_BLOCKING/g, "Non-Blocking")
        .replace(/APPROVE_RELEASE/g, "Approve Release")
        .replace(/BLOCK_RELEASE/g, "Block Release")
        .replace(/HOLD/g, "Hold")
        .replace(/RELEASE/g, "Release");
}