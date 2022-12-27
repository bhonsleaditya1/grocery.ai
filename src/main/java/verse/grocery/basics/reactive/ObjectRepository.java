package verse.grocery.basics.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends ReactiveMongoRepository<ObjectModel, String> {
}
