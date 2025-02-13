package lt.techin.povilas.kartojimas21.repository;

import lt.techin.povilas.kartojimas21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
