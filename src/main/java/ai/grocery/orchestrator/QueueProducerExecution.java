package ai.grocery.orchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

@AllArgsConstructor
@ToString
public class QueueProducerExecution implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(QueueProducerExecution.class);
    private static final KafkaProducer<Integer, String> eventKafkaProducer;
    public static final ObjectMapper objectMapper;

    static {
        log.info("Creating Kafka Producer");
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        eventKafkaProducer = new KafkaProducer<>(producerProperties);
        objectMapper = new ObjectMapper();
        LoggerFactory.getLogger("").atWarn();
    }

    private Event event;
    private Map<ServiceType, Map<Integer, Event>> completedEvents;

    @Override
    public void run() {
        log.info("Started Runnable: {}", this);
        log.info("Executing event: {}", event);
        try {
            eventKafkaProducer.send(new ProducerRecord<>(event.getServiceType().getEvent(), event.getEventId(), objectMapper.writeValueAsString(event)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        while (!completedEvents.get(event.getServiceType()).containsKey(event.getEventId())) {
            try {
                log.info("Waiting for Event {} Response",event.getEventId());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        completedEvents.get(event.getServiceType()).remove(event.getEventId());
        // wait for async call from response queue based on eventId
    }
}
