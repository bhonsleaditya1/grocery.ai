package ai.grocery.basics.kafka.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order{
    Integer orderId;
    Status status;
    User user;
    Integer amount;
}
