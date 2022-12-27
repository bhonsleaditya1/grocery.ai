package verse.grocery.orchestrator;

public class NotADagException extends RuntimeException{
    String message;

    public NotADagException(String message) {
        this.message = message;
    }
}
