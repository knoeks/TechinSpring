package lt.techin.povilas.kartojimas21.repository;

import lt.techin.povilas.kartojimas21.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(String status);
}
