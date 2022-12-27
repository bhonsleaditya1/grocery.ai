package verse.grocery.basics.reactive;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("objectitems")
public class ObjectModel {
    private String id;
    private String data;
}
