package ai.grocery.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Event {
    private Integer eventId;
    private Object data;
    private Status status;
}
