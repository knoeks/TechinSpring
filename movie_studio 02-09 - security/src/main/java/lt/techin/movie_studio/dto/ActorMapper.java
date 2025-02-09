package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Actor;

import java.util.List;

public class ActorMapper {


  public static List<ActorDTO> toActorsDTO(List<Actor> allActors) {
    return allActors.stream().map(ActorMapper::toActorDTO).toList();
  }

  public static ActorDTO toActorDTO(Actor actor) {
    return new ActorDTO(
            actor.getId(),
            actor.getName(),
            actor.getAge()
    );
  }

  public static Actor toActor(ActorDTO actorDTO) {
    return new Actor(
            actorDTO.id(),
            actorDTO.name(),
            actorDTO.age()
    );
  }
}
