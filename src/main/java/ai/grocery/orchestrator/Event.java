package ai.grocery.orchestrator;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event{
    private Integer eventId;
    private Object data;
    private Status status;
    private ServiceType serviceType;
    private InteractionType interactionType;

    public void execute() {
        switch (interactionType){
            case API -> ApiExecutionService.getInstance().execute(this);
            case QUEUE -> QueueExecution.getInstance().execute(this);
        }
    }

    public void rollback() {
        switch (interactionType){
            case API -> ApiExecutionService.getInstance().execute(this);
            case QUEUE -> QueueExecution.getInstance().execute(this);
        }
    }
}
