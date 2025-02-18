package lt.techin.demo.dto;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(Long id,
                                      Long userId,
                                      String eventName,
                                      LocalDateTime registrationDate) {
}
