package lt.techin.demo.repository;

import lt.techin.demo.model.RunningEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningEventRepository extends JpaRepository<RunningEvent, Long> {


}
