package ai.grocery.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
@Log4j2
public class OrchestratorWorkflow implements Runnable {
    private OrchestratorStep rootOrchestratorStep;
    private Map<String, OrchestratorStep> orchestratorStepMap;

    public OrchestratorStep getOrchestratorStepDetails(String orchestratorStepId) {
        return orchestratorStepMap.get(orchestratorStepId);
    }


    public void addToWorkflow(OrchestratorStep orchestratorStep, StepLocation stepLocation) {
        log.info("Adding to Workflow: {},{}", orchestratorStep, stepLocation);
        stepLocation.previousStep.getNext().add(orchestratorStep);
        stepLocation.previousStep.getNext().remove(stepLocation.nextStep);
        orchestratorStep.getNext().add(stepLocation.nextStep);
        if (!isDAG(rootOrchestratorStep)) {
            log.error("Step Creates Cycle!!");
            stepLocation.previousStep.getNext().remove(orchestratorStep);
            orchestratorStep.getNext().remove(stepLocation.nextStep);
            throw new NotADagException("Cycle in Workflow");
        } else {
            orchestratorStepMap.put(orchestratorStep.getOrchestratorStepId(), orchestratorStep);
        }
    }

    public void run() {
        log.info("Executing Workflow");
        if (isDAG(rootOrchestratorStep)) {
            rootOrchestratorStep.setAction(Action.START);
            execute(rootOrchestratorStep, null, null);
        } else {
            throw new NotADagException("Cycle in Workflow");
        }
    }

    private void execute(OrchestratorStep orchestratorStep, OrchestratorStep parentStep, Thread parentThread) {
        log.info("Current Step: {}",orchestratorStep);
        log.info("Parent Step: {}",parentStep);
        log.info("Parent Thread: {}",parentThread);

        if (orchestratorStep == null) return;
        if (parentThread != null) {
            try {
                parentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (parentStep!=null && parentStep.getStatus() == Status.FAILED) {
                rollback();
                return;
            }
        }
        switch (orchestratorStep.getAction()) {
            case START -> {
                log.info("Current Orchestrator Step: {}", orchestratorStep);
                Thread currentThread = new Thread(orchestratorStep,"OrchestratorStep"+"-"+orchestratorStep.getOrchestratorStepId());
                currentThread.start();
                if (!orchestratorStep.getNext().isEmpty()) {
                    orchestratorStep.getNext().parallelStream()
                            .forEach(step -> {
                                if(step!=null) {
                                    step.setAction(Action.START);
                                    execute(step, orchestratorStep, currentThread);
                                }
                            });
                }
            }
            case ROLLED_BACK -> {
                switch (orchestratorStep.getStatus()) {
                    case COMPLETED -> orchestratorStep.setAction(Action.ROLLED_BACK);
                    case NOT_STARTED -> orchestratorStep.setAction(Action.CANCEL);
                }
                Thread currentThread = new Thread(orchestratorStep,"OrchestratorStep"+"-"+orchestratorStep.getOrchestratorStepId());
                currentThread.start();
                if (!orchestratorStep.getNext().isEmpty()) {
                    orchestratorStep.getNext().parallelStream()
                            .forEach(step -> execute(step, orchestratorStep, currentThread));
                }
            }
            case CANCEL -> {
                orchestratorStep.setAction(Action.CANCEL);
                if (!orchestratorStep.getNext().isEmpty()) {
                    orchestratorStep.getNext().parallelStream()
                            .forEach(step -> execute(step, orchestratorStep, null));
                }
            }
        }
    }

    private void rollback() {
        rootOrchestratorStep.setAction(Action.ROLLED_BACK);
        execute(rootOrchestratorStep, null, null);
    }

    public boolean isDAG(OrchestratorStep orchestratorStep) {
        return true;
    }
}
