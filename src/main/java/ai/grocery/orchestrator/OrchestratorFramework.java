package ai.grocery.orchestrator;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.*;

public class OrchestratorFramework {

    static {
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
        QueueExecution.getInstance();
        ApiExecutionService.getInstance();
    }

    public OrchestratorWorkflow createWorkflow(OrchestratorStep orchestratorStep) {
        OrchestratorWorkflow orchestratorWorkflow = new OrchestratorWorkflow();
        orchestratorWorkflow.setRootOrchestratorStep(orchestratorStep);
        orchestratorWorkflow.setOrchestratorStepMap(new HashMap<>());
        return orchestratorWorkflow;
    }

    public void addToWorkflow(OrchestratorWorkflow orchestratorWorkflow, OrchestratorStep orchestratorStep, StepLocation stepLocation) {
        orchestratorWorkflow.addToWorkflow(orchestratorStep, stepLocation);
    }

    public void execute(OrchestratorWorkflow orchestratorWorkflow) {
        orchestratorWorkflow.executeWorkflow();
    }
}
