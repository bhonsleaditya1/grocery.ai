package verse.grocery.orchestrator;


import verse.grocery.notification.NotificationMicroservice;
import verse.grocery.payments.PaymentsMicroservice;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class OrchestratorTest {
    public static final Logger log = Logger.getLogger(OrchestratorTest.class.getName());
    public static void main(String[] args) {
        log.setLevel(Level.INFO);
        OrchestratorFramework orchestratorFramework = new OrchestratorFramework();
        NotificationMicroservice notificationMicroservice = new NotificationMicroservice();
        Thread notifications = new Thread(notificationMicroservice,"NotificationMicroservice");
        notifications.start();
        PaymentsMicroservice paymentsMicroservice = new PaymentsMicroservice();
        Thread payments = new Thread(paymentsMicroservice,"PaymentsMicroservice");
        payments.start();
        Event pay = new Event(1, "pay me;?", Status.NOT_STARTED,ServiceType.PAYMENTS,InteractionType.QUEUE);
        OrchestratorStep payment = OrchestratorStep.builder()
                .orchestratorStepId("step-1")
                .event(pay)
                .next(new ArrayList<>())
                .retryCount(0)
                .build();
        OrchestratorWorkflow orchestratorWorkflow = orchestratorFramework.createWorkflow(payment);
        StepLocation stepLocation = new StepLocation();
        stepLocation.setPreviousStep(payment);
        Event notif = new Event(2, "notif", Status.NOT_STARTED,ServiceType.NOTIFICATION,InteractionType.QUEUE);
        OrchestratorStep notification = OrchestratorStep.builder()
                .orchestratorStepId("step-2")
                .event(notif)
                .next(new ArrayList<>())
                .retryCount(0)
                .build();
        orchestratorFramework.addToWorkflow(orchestratorWorkflow, notification, stepLocation);
        Event notif1 = new Event(3, "notif1", Status.NOT_STARTED,ServiceType.NOTIFICATION,InteractionType.QUEUE);
        OrchestratorStep notification1 = OrchestratorStep.builder()
                .orchestratorStepId("step-3")
                .event(notif1)
                .next(new ArrayList<>())
                .retryCount(0)
                .build();
        orchestratorFramework.addToWorkflow(orchestratorWorkflow, notification1, stepLocation);
        orchestratorFramework.execute(orchestratorWorkflow);
        log.info(orchestratorWorkflow.toString());
    }
}
