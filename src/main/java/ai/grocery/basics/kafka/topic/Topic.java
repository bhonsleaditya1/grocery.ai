package ai.grocery.basics.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topic {
    @Bean
    public NewTopic transactionTopic(){
        return TopicBuilder.name("transaction")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic paymentTopic(){
        return TopicBuilder.name("payment")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder.name("order")
                .partitions(5)
                .build();
    }

}
