package lt.techin.demo.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventResponseDTO(Long id,
                                      String name,
                                      LocalDate calendarDate,
                                      String location,
                                      Integer maxParticipants) {
}
