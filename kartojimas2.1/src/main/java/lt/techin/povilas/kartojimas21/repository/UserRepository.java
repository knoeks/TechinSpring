package lt.techin.povilas.kartojimas21.repository;

import lt.techin.povilas.kartojimas21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String Username);

}
