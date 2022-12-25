package ai.grocery.basics.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    public Mono<ObjectModel> getByObjectId(String id){
        return objectRepository.findById(id);
    }

    public Mono<ObjectModel> createObject(ObjectModel objectModel){
        return objectRepository.insert(objectModel);
    }
    public Mono<ObjectModel> updateObject(ObjectModel objectModel){
        return objectRepository.save(objectModel);
    }
    public Mono<Void> deleteByObjectId(String id){
        return objectRepository.deleteById(id);
    }
}
