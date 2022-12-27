
## grocery.verse
Reactive Microservice Project
### Components:

---
Microservices
 - [x] Orchestrator Framework (The Beast)
 - [ ] Chat
 - [ ] KYC
 - [ ] Notification
 - [ ] Analytics
 - [ ] Delivery
 - [ ] OAuth
 - [ ] Payments
 - [ ] Review 
 - [ ] Rewards
 - 
### Tech Stack

---
 - [x] Java 17
 - [x] Multi-Threading
 - [ ] Project Reactor
 - [ ] Reactive MongoDB
 - [x] Kafka
 - [x] Docker
 - [ ] R2DBMS: Postgres
 - [ ] graphQL
 - [ ] gRPC
 - [ ] WebSocket
---

## Into to Multiverse

---
### Orchestrator Framework (The Beast)
Motivation: Central Orchestrator for Executing Workflows
#### Log
```text
21:29:20.474 [main] INFO  a.g.o.OrchestratorFramework - Creating Kafka Topics
21:29:22.421 [main] INFO  a.g.o.OrchestratorFramework - Creating Queue Execution Instance
21:29:22.431 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.078 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-KYC,5,main],KYC
21:29:23.078 [QueueConsumerExecution-KYC] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@ffe66d29
21:29:23.080 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.110 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-CHAT,5,main],CHAT
21:29:23.111 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.111 [QueueConsumerExecution-CHAT] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@d5e33ebc
21:29:23.142 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-NOTIFICATION,5,main],NOTIFICATION
21:29:23.142 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.142 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@6e923742
21:29:23.167 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-PAYMENTS,5,main],PAYMENTS
21:29:23.168 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.167 [QueueConsumerExecution-PAYMENTS] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@824fc2b9
21:29:23.216 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-REVIEW,5,main],REVIEW
21:29:23.216 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.221 [QueueConsumerExecution-REVIEW] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@e853e554
21:29:23.247 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-REWARDS,5,main],REWARDS
21:29:23.248 [main] INFO  a.g.o.QueueConsumerExecution - Creating Kafka Consumer
21:29:23.248 [QueueConsumerExecution-REWARDS] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@10793a2e
21:29:23.276 [main] INFO  a.g.orchestrator.QueueExecution - Created parallel thread: Thread[QueueConsumerExecution-DELIVERY,5,main],DELIVERY
21:29:23.276 [main] INFO  a.g.o.OrchestratorFramework - Creating API Execution Instance
21:29:23.278 [QueueConsumerExecution-DELIVERY] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@87e55934
21:29:23.520 [main] INFO  a.g.n.NotificationMicroservice - STARTING NOTIFICATION MICROSERVICE
21:29:23.520 [main] INFO  a.g.n.NotificationMicroservice - Creating Kafka Consumer
21:29:23.535 [main] INFO  a.g.n.NotificationMicroservice - Creating Kafka Producer
21:29:23.592 [NotificationMicroservice] INFO  a.g.n.NotificationMicroservice - Starting Notification Microservice: ai.grocery.notification.NotificationMicroservice@b068333f
21:29:23.593 [main] INFO  a.g.payments.PaymentsMicroservice - STARTING PAYMENTS MICROSERVICE
21:29:23.594 [main] INFO  a.g.payments.PaymentsMicroservice - Creating Kafka Consumer
21:29:23.615 [main] INFO  a.g.payments.PaymentsMicroservice - Creating Kafka Producer
21:29:23.626 [PaymentsMicroservice] INFO  a.g.payments.PaymentsMicroservice - Starting Payments Microservice: ai.grocery.payments.PaymentsMicroservice@345b8ce2
21:29:23.697 [main] INFO  a.g.o.OrchestratorFramework - Created Workflow: OrchestratorWorkflow(rootOrchestratorStep=OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[]), orchestratorStepMap={})
21:29:23.698 [main] INFO  a.g.o.OrchestratorWorkflow - Adding to Workflow: OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[]),StepLocation(previousStep=OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[]), nextStep=null)
21:29:23.699 [main] INFO  a.g.o.OrchestratorWorkflow - Adding to Workflow: OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[]),StepLocation(previousStep=OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null])]), nextStep=null)
21:29:23.700 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Executing Workflow
21:29:23.701 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Current Step: OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null]), OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null])])
21:29:23.701 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Parent Step: null
21:29:23.701 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Parent Thread: null
21:29:23.703 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Current Orchestrator Step: OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null]), OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null])])
21:29:23.704 [OrchestratorStep-step-1] INFO  a.g.orchestrator.OrchestratorStep - Started Execution of Step:Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE),0
21:29:23.710 [OrchestratorStep-step-1] INFO  a.g.o.QueueProducerExecution - Creating Kafka Producer
21:29:23.715 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Current Step: OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])
21:29:23.715 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Parent Step: OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=1, status=null, action=START, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null]), OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])])
21:29:23.716 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Parent Thread: Thread[OrchestratorStep-step-1,5,main]
21:29:23.719 [ForkJoinPool.commonPool-worker-1] INFO  a.g.o.OrchestratorWorkflow - Current Step: OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])
21:29:23.720 [ForkJoinPool.commonPool-worker-1] INFO  a.g.o.OrchestratorWorkflow - Parent Step: OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=1, status=null, action=START, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null]), OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])])
21:29:23.720 [ForkJoinPool.commonPool-worker-1] INFO  a.g.o.OrchestratorWorkflow - Parent Thread: Thread[OrchestratorStep-step-1,5,main]
21:29:23.728 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Started Runnable: QueueProducerExecution(event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), completedEvents={NOTIFICATION={}, REWARDS={}, CHAT={}, PAYMENTS={}, KYC={}, REVIEW={}, DELIVERY={}})
21:29:23.731 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Executing event: Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE)
Dec 27, 2022 9:29:23 PM ai.grocery.orchestrator.OrchestratorTest main
INFO: OrchestratorWorkflow(rootOrchestratorStep=OrchestratorStep(orchestratorStepId=step-1, event=Event(eventId=1, data=pay me;?, status=NOT_STARTED, serviceType=PAYMENTS, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null]), OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null])]), orchestratorStepMap={step-3=OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null]), step-2=OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=null, next=[null])})
21:29:23.892 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:24.895 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:25.905 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:26.917 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:27.926 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:28.932 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:29.941 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:30.951 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:31.964 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:32.950 [PaymentsMicroservice] INFO  a.g.payments.PaymentsMicroservice - Payments Event: Event(eventId=1, data=pay me;?, status=COMPLETED, serviceType=PAYMENTS, interactionType=QUEUE)
21:29:32.970 [QueueConsumerExecution-PAYMENTS] INFO  a.g.o.QueueConsumerExecution - Polling for Event: ai.grocery.orchestrator.QueueConsumerExecution@824fc2b9
21:29:32.978 [QueueProducerExecution-1] INFO  a.g.o.QueueProducerExecution - Waiting for Event 1 Response
21:29:32.981 [QueueConsumerExecution-PAYMENTS] INFO  a.g.o.QueueConsumerExecution - Event Completed: Event(eventId=1, data=pay me;?, status=COMPLETED, serviceType=PAYMENTS, interactionType=QUEUE)
21:29:33.978 [PaymentsMicroservice] INFO  a.g.payments.PaymentsMicroservice - Starting Payments Microservice: ai.grocery.payments.PaymentsMicroservice@345b8ce2
21:29:33.993 [QueueConsumerExecution-PAYMENTS] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@824fc2b9
21:29:33.994 [OrchestratorWorkflow] INFO  a.g.o.OrchestratorWorkflow - Current Orchestrator Step: OrchestratorStep(orchestratorStepId=step-3, event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])
21:29:33.994 [ForkJoinPool.commonPool-worker-1] INFO  a.g.o.OrchestratorWorkflow - Current Orchestrator Step: OrchestratorStep(orchestratorStepId=step-2, event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), rollback=null, retryCount=0, status=null, action=START, next=[null])
21:29:33.994 [OrchestratorStep-step-2] INFO  a.g.orchestrator.OrchestratorStep - Started Execution of Step:Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE),0
21:29:33.994 [OrchestratorStep-step-3] INFO  a.g.orchestrator.OrchestratorStep - Started Execution of Step:Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE),0
21:29:33.996 [QueueProducerExecution-2] INFO  a.g.o.QueueProducerExecution - Started Runnable: QueueProducerExecution(event=Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), completedEvents={NOTIFICATION={}, REWARDS={}, CHAT={}, PAYMENTS={}, KYC={}, REVIEW={}, DELIVERY={}})
21:29:33.996 [QueueProducerExecution-3] INFO  a.g.o.QueueProducerExecution - Started Runnable: QueueProducerExecution(event=Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE), completedEvents={NOTIFICATION={}, REWARDS={}, CHAT={}, PAYMENTS={}, KYC={}, REVIEW={}, DELIVERY={}})
21:29:33.996 [QueueProducerExecution-2] INFO  a.g.o.QueueProducerExecution - Executing event: Event(eventId=2, data=notif, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:33.996 [QueueProducerExecution-3] INFO  a.g.o.QueueProducerExecution - Executing event: Event(eventId=3, data=notif1, status=NOT_STARTED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:34.003 [QueueProducerExecution-2] INFO  a.g.o.QueueProducerExecution - Waiting for Event 2 Response
21:29:34.003 [QueueProducerExecution-3] INFO  a.g.o.QueueProducerExecution - Waiting for Event 3 Response
21:29:34.016 [NotificationMicroservice] INFO  a.g.n.NotificationMicroservice - Notification Event: Event(eventId=2, data=notif, status=COMPLETED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:34.039 [NotificationMicroservice] INFO  a.g.n.NotificationMicroservice - Notification Event: Event(eventId=3, data=notif1, status=COMPLETED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:34.048 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Polling for Event: ai.grocery.orchestrator.QueueConsumerExecution@6e923742
21:29:34.056 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Event Completed: Event(eventId=2, data=notif, status=COMPLETED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:35.015 [QueueProducerExecution-3] INFO  a.g.o.QueueProducerExecution - Waiting for Event 3 Response
21:29:35.046 [NotificationMicroservice] INFO  a.g.n.NotificationMicroservice - Starting Notification Microservice: ai.grocery.notification.NotificationMicroservice@b068333f
21:29:35.062 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@6e923742
21:29:35.063 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Polling for Event: ai.grocery.orchestrator.QueueConsumerExecution@6e923742
21:29:35.065 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Event Completed: Event(eventId=3, data=notif1, status=COMPLETED, serviceType=NOTIFICATION, interactionType=QUEUE)
21:29:36.075 [QueueConsumerExecution-NOTIFICATION] INFO  a.g.o.QueueConsumerExecution - Starting Runnable: ai.grocery.orchestrator.QueueConsumerExecution@6e923742
```
