package ai.grocery.orchestrator;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class QueueExecution implements StepExecution {

    private static final KafkaConsumer<Integer, Event> eventKafkaConsumer;
    private static final KafkaProducer<Integer, Event> eventKafkaProducer;
    private static Map<ServiceType,Map<Integer, Event>> completedEvents;

    static {
        String bootstrapServer = "localhost:9092";
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "grocery");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        eventKafkaConsumer = new KafkaConsumer<>(consumerProperties);

        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        eventKafkaProducer = new KafkaProducer<>(producerProperties);

        completedEvents = new HashMap<>();
        for(ServiceType serviceType: ServiceType.values()){
            completedEvents.put(serviceType,new HashMap<>());
            getInstance().setService(serviceType);
            Thread queue = new Thread(()-> getInstance().process());
            try {
                queue.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static QueueExecution instance = null;
    private ServiceType microServiceType;

    public static QueueExecution getInstance(){
        if(instance ==null){
            instance = new QueueExecution();
        }
        return instance;
    }

    @Override
    public void execute(Event event) {
        eventKafkaProducer.send(new ProducerRecord<>(microServiceType.getEvent(), event.getEventId(), event));
        Thread poll = new Thread(()-> {
            while (!completedEvents.get(microServiceType).containsKey(event.getEventId())) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            completedEvents.get(microServiceType).remove(event.getEventId());
        });
        try{
            poll.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        // wait for async call from response queue based on eventId
    }

    @Override
    public void rollback(Event event) {
        eventKafkaProducer.send(new ProducerRecord<>(microServiceType.getEvent(), event.getEventId(), event));
    }

    @Override
    public void process() {
        eventKafkaConsumer.subscribe(Collections.singleton(microServiceType.getResponse()));
        ConsumerRecords<Integer, Event> records = eventKafkaConsumer.poll(Duration.ofMinutes(1));
        records.forEach(record -> {
            Event event = record.value();
            switch (event.getStatus()) {
                case COMPLETED -> log.info("Event Completed: {}", event);
                case FAILED -> log.error("Event Failed: {}", event);
            }
            Map<Integer,Event> eventMap = completedEvents.get(microServiceType);
            eventMap.put(event.getEventId(), event);
        });
    }

    @Override
    public void setService(ServiceType serviceType) {
        this.microServiceType = serviceType;
    }
}
