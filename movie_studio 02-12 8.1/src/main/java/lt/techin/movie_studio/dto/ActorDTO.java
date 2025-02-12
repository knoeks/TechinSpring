package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lt.techin.movie_studio.validation.Person;

public record ActorDTO(Long id,

                       @Size(max = 60)
                       @Person
                       String name,

                       @Positive
                       Short age
) {
}
