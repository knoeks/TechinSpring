package lt.techin.movie_studio.repository;

import lt.techin.movie_studio.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

}
