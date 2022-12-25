package ai.grocery.orchestrator;

public interface StepExecution {
    void execute(Event event);

    void rollback(Event event);

    void process();

    void setService(ServiceType serviceType);
}
