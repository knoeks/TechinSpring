package lt.techin.MoviesProject.repository;

import lt.techin.MoviesProject.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
