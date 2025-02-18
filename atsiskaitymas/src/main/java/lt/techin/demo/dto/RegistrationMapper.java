package lt.techin.demo.dto;

import lt.techin.demo.model.Registration;
import lt.techin.demo.model.RunningEvent;

import java.time.LocalDateTime;
import java.util.Optional;

public class RegistrationMapper {

  public static Registration toRegistration(RegistrationRequestDTO registrationRequestDTO, RunningEvent runningEvent) {
    return new Registration(null,
            registrationRequestDTO.user(),
            runningEvent,
            LocalDateTime.now()
    );
  }

  public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {
    return new RegistrationResponseDTO(
            registration.getId(),
            registration.getUser().getId(),
            registration.getRunningEvent().getName(),
            registration.getRegistrationDate()
    );
  }

}
