package ai.grocery.orchestrator;


import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
public class OrchestratorTest {
    public static void main(String[] args) {
        OrchestratorFramework orchestratorFramework = new OrchestratorFramework();
        Event pay = new Event(1, "pay me;?", Status.NOT_STARTED);
        OrchestratorStep payment = OrchestratorStep.builder()
                .orchestratorStepId("step-1")
                .event(pay)
                .microServiceType(ServiceType.PAYMENTS)
                .interactionType(InteractionType.QUEUE)
                .next(new ArrayList<>())
                .retryCount(0)
                .build();
        OrchestratorWorkflow orchestratorWorkflow = orchestratorFramework.createWorkflow(payment);
        StepLocation stepLocation = new StepLocation();
        stepLocation.setPreviousStep(payment);
        Event notif = new Event(2, "notif", Status.NOT_STARTED);
        OrchestratorStep notification = OrchestratorStep.builder()
                .orchestratorStepId("step-2")
                .event(notif)
                .microServiceType(ServiceType.NOTIFICATION)
                .interactionType(InteractionType.QUEUE)
                .next(new ArrayList<>())
                .retryCount(0)
                .build();
        orchestratorFramework.addToWorkflow(orchestratorWorkflow, notification, stepLocation);
        orchestratorFramework.execute(orchestratorWorkflow);
        log.info("{}", orchestratorWorkflow);
    }
}
