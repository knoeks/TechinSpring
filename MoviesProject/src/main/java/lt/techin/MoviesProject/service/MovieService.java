package lt.techin.MoviesProject.service;

import lt.techin.MoviesProject.model.Movie;
import lt.techin.MoviesProject.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
  private final MovieRepository movieRepository;

  // ta kvieciama objekta injectina
  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  
}
