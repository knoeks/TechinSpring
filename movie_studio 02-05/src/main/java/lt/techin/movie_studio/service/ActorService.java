package lt.techin.movie_studio.service;

import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
  private ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public List<Actor> findAllActors() {
    return actorRepository.findAll();
  }

  public Optional<Actor> findActorById(Long id) {
    return actorRepository.findById(id);
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }
}
