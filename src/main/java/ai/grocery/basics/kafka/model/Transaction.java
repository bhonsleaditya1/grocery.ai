package ai.grocery.basics.kafka.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    Integer transactionId;
    Status status;
    Payment payment;
}
