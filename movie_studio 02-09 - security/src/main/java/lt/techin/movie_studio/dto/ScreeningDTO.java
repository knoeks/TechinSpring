package lt.techin.movie_studio.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScreeningDTO(Long id,
                           @NotBlank
                           @Size(max = 3)
                           String hall,
                           @Future
                           @NotNull
                           @Column(name = "screening_time")
                           LocalDateTime Screening_time
) {
}
