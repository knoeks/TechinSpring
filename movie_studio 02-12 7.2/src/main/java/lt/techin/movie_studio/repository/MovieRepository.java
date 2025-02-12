package lt.techin.movie_studio.repository;

import lt.techin.movie_studio.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {


}
