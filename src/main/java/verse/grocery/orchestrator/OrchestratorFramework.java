package verse.grocery.orchestrator;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.*;

@Log4j2
public class OrchestratorFramework {

    static {
        log.info("Creating Kafka Topics");
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            List<NewTopic> newTopicList = new ArrayList<>();
            Arrays.stream(ServiceType.values()).forEach(serviceType -> {
                newTopicList.add(new NewTopic(serviceType.getEvent(), partitions, replicationFactor));
                newTopicList.add(new NewTopic(serviceType.getEvent(), partitions, replicationFactor));
            });
            CreateTopicsResult result = admin.createTopics(newTopicList);
        }
        log.info("Creating Queue Execution Instance");
        QueueExecution.getInstance();
        log.info("Creating API Execution Instance");
        ApiExecutionService.getInstance();
    }

    public OrchestratorWorkflow createWorkflow(OrchestratorStep orchestratorStep) {
        OrchestratorWorkflow orchestratorWorkflow = new OrchestratorWorkflow(orchestratorStep,new HashMap<>());
        log.info("Created Workflow: {}",orchestratorWorkflow);
        return orchestratorWorkflow;
    }

    public void addToWorkflow(OrchestratorWorkflow orchestratorWorkflow, OrchestratorStep orchestratorStep, StepLocation stepLocation) {
        orchestratorWorkflow.addToWorkflow(orchestratorStep, stepLocation);
    }

    public void execute(OrchestratorWorkflow orchestratorWorkflow) {
        new Thread(orchestratorWorkflow,"OrchestratorWorkflow").start();
    }
}
