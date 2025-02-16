package lt.techin.povilas.kartojimas21.repository;

import lt.techin.povilas.kartojimas21.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
