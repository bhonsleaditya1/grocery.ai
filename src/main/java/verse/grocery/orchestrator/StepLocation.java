package verse.grocery.orchestrator;

import lombok.Data;

@Data
public class StepLocation {
    OrchestratorStep previousStep;
    OrchestratorStep nextStep;
}
