package ai.grocery.orchestrator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class OrchestratorStep implements Runnable{
    public static final Logger log = LoggerFactory.getLogger(OrchestratorStep.class);
    private String orchestratorStepId;
    private Event event;
    private Event rollback;
    private Integer retryCount;
    private Status status;
    private Action action;
    private List<OrchestratorStep> next;


    public void run(){
        log.info("Started Execution of Step:{},{}",event,retryCount);
        retryCount++;
        event.execute();
        if(retryCount>5) {
            log.error("Rollback of Event: {}",event);
            event.rollback();
        }
    }
}
