package lt.techin.demo.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventRequestDTO(@NotNull
                                     @Size(min = 5, max = 255)
                                     String name,
                                     @NotNull
                                     @Future
                                     LocalDate calendarDate,
                                     @NotNull
                                     @Pattern(regexp = "^[a-zA-Z0-9]{0,200}$", message = "location must be letters, numbers only and upto 200 characters long.")
                                     String location,
                                     @Positive
                                     Integer maxParticipants) {
}
