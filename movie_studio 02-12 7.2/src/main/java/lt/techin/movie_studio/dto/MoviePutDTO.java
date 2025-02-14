package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Screening;
import lt.techin.movie_studio.validation.Person;

import java.util.List;

public record MoviePutDTO(
                          @NotBlank
                          @Size(max = 50)
                          String title,
                          @Person
                          String director,
                          List<Screening> screenings,
                          List<Actor> actors) {
}
