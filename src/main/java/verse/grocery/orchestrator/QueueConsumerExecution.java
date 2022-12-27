package verse.grocery.orchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class QueueConsumerExecution implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(QueueConsumerExecution.class);
    private final KafkaConsumer<Integer, String> eventKafkaConsumer;
    public ObjectMapper objectMapper;
    private final ServiceType microServiceType;
    private final Map<Integer, Event> completedEvents;
    QueueConsumerExecution(ServiceType serviceType, Map<Integer,Event> completedEvents) {
        this.microServiceType = serviceType;
        this.completedEvents = completedEvents;
        log.info("Creating Kafka Consumer");
        String bootstrapServer = "localhost:9092";
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "grocery");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerProperties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "8000");
        eventKafkaConsumer = new KafkaConsumer<>(consumerProperties);
        objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        while(true) {
            log.info("Starting Runnable: {}", this);
            eventKafkaConsumer.subscribe(Collections.singleton(microServiceType.getResponse()));
            ConsumerRecords<Integer, String> records = eventKafkaConsumer.poll(Duration.ofMinutes(1));
            log.info("Polling for Event: {}", this);
            records.forEach(record -> {
                try {
                    Event event = objectMapper.readValue(record.value(), Event.class);
                    switch (event.getStatus()) {
                        case COMPLETED -> log.info("Event Completed: {}", event);
                        case FAILED -> log.error("Event Failed: {}", event);
                    }
                    completedEvents.put(event.getEventId(), event);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
