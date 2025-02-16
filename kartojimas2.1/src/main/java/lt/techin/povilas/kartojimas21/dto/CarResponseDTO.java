package lt.techin.povilas.kartojimas21.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.povilas.kartojimas21.model.Rental;

import java.util.List;

public record CarResponseDTO(Long id,
                             @NotBlank
                             @Size(min = 2, max = 50)
                             String brand,
                             @NotBlank
                             @Size(min = 2, max = 50)
                             String model,
                             @NotNull
                             Integer year,
                             @NotBlank
                             String status
                             ) {
}
