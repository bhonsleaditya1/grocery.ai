package verse.grocery.multiverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan("verse.grocery")
@EnableReactiveMongoRepositories("verse.grocery")
@EnableKafka
@EnableScheduling
public class MultiverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiverseApplication.class, args);
    }

}
