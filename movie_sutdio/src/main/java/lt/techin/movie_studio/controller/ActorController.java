package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ActorController {
  private ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<Actor>> getActors() {
    return ResponseEntity.ok(actorService.findAllActors());
  }


  @GetMapping("/actors/{id}")
  public ResponseEntity<Actor> getActor(@Valid @PathVariable Long id) {
    Optional<Actor> optionalActor = actorService.findActorById(id);

    if (optionalActor.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optionalActor.get());
  }

  @PostMapping("/actors")
  public ResponseEntity<Actor> saveActor(@Valid @RequestBody Actor actor) {
    Actor savedActor = actorService.saveActor(actor);

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedActor.getId())
                    .toUri()
    ).body(savedActor);
  }
}
