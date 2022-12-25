package ai.grocery.orchestrator;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class ApiExecutionService implements StepExecution {
    private RestTemplate restTemplate = new RestTemplate();

    private static ApiExecutionService instance =null;
    public static ApiExecutionService getInstance(){
        if(instance ==null){
            instance = new ApiExecutionService();
        }
        return instance;
    }

    @Override
    public void execute(Event event) {
    }

    @Override
    public void rollback(Event event) {
    }

    @Override
    public void process() {
    }

    @Override
    public void setService(ServiceType serviceType) {

    }


}
