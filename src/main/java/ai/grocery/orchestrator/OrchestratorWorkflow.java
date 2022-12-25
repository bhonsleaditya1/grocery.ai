package ai.grocery.orchestrator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class OrchestratorWorkflow {
    @Setter
    private OrchestratorStep rootOrchestratorStep;
    private ExecutionMethod executionMethod;
    @Setter
    private Map<String, OrchestratorStep> orchestratorStepMap;

    public OrchestratorStep getOrchestratorStepDetails(String orchestratorStepId){
        return orchestratorStepMap.get(orchestratorStepId);
    }


    public void addToWorkflow(OrchestratorStep orchestratorStep, StepLocation stepLocation) {
        stepLocation.previousStep.getNext().add(orchestratorStep);
        orchestratorStep.getNext().add(stepLocation.nextStep);
        if (!isDAG(rootOrchestratorStep)) {
            stepLocation.previousStep.getNext().remove(orchestratorStep);
            orchestratorStep.getNext().remove(stepLocation.nextStep);
            throw new NotADagException("Cycle in Workflow");
        }else{
            orchestratorStepMap.put(orchestratorStep.getOrchestratorStepId(),orchestratorStep);
        }
    }

    public void executeWorkflow() {
        if (isDAG(rootOrchestratorStep)) {
            execute(rootOrchestratorStep);
        } else {
            throw new NotADagException("Cycle in Workflow");
        }
    }

    private void execute(OrchestratorStep orchestratorStep) {
        if(orchestratorStep==null) return;
        orchestratorStep.execute();
        if (!orchestratorStep.getNext().isEmpty()) {
            orchestratorStep.getNext().parallelStream().forEach(this::execute);
        }
    }

    public boolean isDAG(OrchestratorStep orchestratorStep) {
        return true;
    }
}
