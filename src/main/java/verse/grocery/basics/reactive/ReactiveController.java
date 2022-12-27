package verse.grocery.basics.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class ReactiveController {

    @Autowired
    private ObjectService objectService;

    @GetMapping("")
    public ResponseEntity<Mono<String>> welcome(){
        return new ResponseEntity<>(Mono.just("Welcome"),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ObjectModel>> getByObjectId(@PathVariable("id") String id){
        return new ResponseEntity<>(objectService.getByObjectId(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Mono<ObjectModel>> createObject(@RequestBody ObjectModel objectModel){
        return new ResponseEntity<>(objectService.createObject(objectModel), HttpStatus.ACCEPTED);
    }

    @PutMapping("/")
    public ResponseEntity<Mono<ObjectModel>> updateObject(@RequestBody ObjectModel objectModel){
        return new ResponseEntity<>(objectService.updateObject(objectModel), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteByObjectId(@PathVariable("id") String id){
        return new ResponseEntity<>(objectService.deleteByObjectId(id), HttpStatus.ACCEPTED);
    }
}
