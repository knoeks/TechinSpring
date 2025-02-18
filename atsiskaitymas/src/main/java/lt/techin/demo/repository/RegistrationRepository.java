package lt.techin.demo.repository;

import lt.techin.demo.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
  List<Registration> findByRunningEventId(Long eventId);

}
