package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Screening;

import java.util.List;

public record MovieDTO(Long id,
                       String title,
                       String director,
                       List<Screening> screenings,
                       List<Actor> actors) {
}
