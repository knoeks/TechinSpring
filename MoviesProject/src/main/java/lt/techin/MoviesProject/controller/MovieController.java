package lt.techin.MoviesProject.controller;

import lt.techin.MoviesProject.model.Movie;
import lt.techin.MoviesProject.repository.MovieRepository;
import lt.techin.MoviesProject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {
  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
    Optional<Movie> optionalMovie = movieService.findMovieById(id);

    if (optionalMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optionalMovie.get());
  }


  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@RequestBody Movie movie) {
    // validate:

    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Title cannot be blank");
    }

    if (movie.getTitle().length() > 100) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Title length exceeded");
    }

    if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Director cannot be blank");
    }

    if (movie.getDirector().length() > 50) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Director length exceeded");
    }

    Movie savedMovie = movieService.saveMovie(movie);


    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedMovie.getId())
                            .toUri()
            )
            .body(movie);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Title cannot be blank");
    }

    if (movie.getTitle().length() > 100) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Title length exceeded");
    }

    if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Director cannot be blank");
    }

    if (movie.getDirector().length() > 50) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Director length exceeded");
    }

    // cia jau patikrintas movie - tvarkingas
    // pakeist jei egzistuoja
    if (movieService.existsMovieById(id)) {
      Movie updatedMovie = movieService.findMovieById(id).get();

      updatedMovie.setDirector(movie.getDirector());
      updatedMovie.setTitle(movie.getTitle());


      return ResponseEntity.ok(movieService.saveMovie(updatedMovie));
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("api/movies/{id}")
                            .buildAndExpand(savedMovie.getId())
                            .toUri()
            )
            .body(savedMovie);
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existsMovieById(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }
}
