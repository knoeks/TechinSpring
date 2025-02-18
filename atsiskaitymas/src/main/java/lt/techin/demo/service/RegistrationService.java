package lt.techin.demo.service;

import lt.techin.demo.model.Registration;
import lt.techin.demo.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
  private final RegistrationRepository registrationRepository;

  @Autowired
  public RegistrationService(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  public Registration saveRegistration(Registration registration) {
    return registrationRepository.save(registration);
  }

  public List<Registration> findRegistrationsByEventId(Long eventId) {
    return registrationRepository.findByRunningEventId(eventId);
  }


}
