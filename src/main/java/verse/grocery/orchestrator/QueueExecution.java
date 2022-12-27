package verse.grocery.orchestrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class QueueExecution implements EventExecution {

    private static final Logger log = LoggerFactory.getLogger(QueueExecution.class);

    private static QueueExecution instance;
    public static QueueExecution getInstance(){
        if(instance==null){
            instance = new QueueExecution();
        }
        return instance;
    }

    private static final Map<ServiceType, Map<Integer,Event>> completedEvents=new HashMap<>();
    static {
        for(ServiceType serviceType: ServiceType.values()){
            completedEvents.put(serviceType,new HashMap<>());
            QueueConsumerExecution queueConsumerExecution = new QueueConsumerExecution(serviceType,completedEvents.get(serviceType));
            Thread queue = new Thread(queueConsumerExecution,"QueueConsumerExecution"+"-"+serviceType);
            try {
                queue.start();
                log.info("Created parallel thread: {},{}",queue,serviceType);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void execute(Event event) {
        Thread thread = new Thread(new QueueProducerExecution(event,completedEvents),"QueueProducerExecution"+"-"+event.getEventId());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
