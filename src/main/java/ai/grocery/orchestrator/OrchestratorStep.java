package ai.grocery.orchestrator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class OrchestratorStep {
    private String orchestratorStepId;
    private ServiceType microServiceType;
    private InteractionType interactionType;
    private Event event;
    private StepExecution stepExecution;
    private Integer retryCount;
    private List<OrchestratorStep> next;


    public void execute(){
        switch (interactionType){
            case QUEUE -> {stepExecution = QueueExecution.getInstance();}
            case API -> stepExecution = ApiExecutionService.getInstance();
        }
        retryCount++;
        stepExecution.setService(microServiceType);
        stepExecution.execute(event);
        if(retryCount>5) {
            stepExecution.rollback(event);
        }
    }
}
