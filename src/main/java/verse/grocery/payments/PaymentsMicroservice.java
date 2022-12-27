package verse.grocery.payments;

import verse.grocery.orchestrator.Event;
import verse.grocery.orchestrator.ServiceType;
import verse.grocery.orchestrator.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@AllArgsConstructor
public class PaymentsMicroservice implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(PaymentsMicroservice.class);
    public static final ObjectMapper objectMapper;
    private static final KafkaConsumer<Integer, String> eventKafkaConsumer;
    private static final KafkaProducer<Integer, String> eventKafkaProducer;

    static {
        log.info("STARTING PAYMENTS MICROSERVICE");
        log.info("Creating Kafka Consumer");
        String bootstrapServer = "localhost:9092";
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "grocery");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        consumerProperties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"8000");
        eventKafkaConsumer = new KafkaConsumer<>(consumerProperties);

        log.info("Creating Kafka Producer");
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        eventKafkaProducer = new KafkaProducer<>(producerProperties);

        objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        while(true) {
            log.info("Starting Payments Microservice: {}", this);
            eventKafkaConsumer.subscribe(Collections.singleton(ServiceType.PAYMENTS.getEvent()));
            eventKafkaConsumer.poll(Duration.ofMinutes(1)).forEach(record -> {
                try {
                    Event event = objectMapper.readValue(record.value(), Event.class);
                    event.setStatus(Status.COMPLETED);
                    log.info("Payments Event: {}", event);
                    eventKafkaProducer.send(new ProducerRecord<>(ServiceType.PAYMENTS.getResponse(), event.getEventId(), objectMapper.writeValueAsString(event)));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
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
