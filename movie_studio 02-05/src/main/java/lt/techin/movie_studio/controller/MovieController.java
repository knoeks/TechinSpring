package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import lt.techin.movie_studio.dto.MovieDTO;
import lt.techin.movie_studio.dto.MovieMapper;
import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<MovieDTO>> getMovies() {
    return ResponseEntity.ok(MovieMapper.toMovieDTOList(movieService.findAllMovies()));
  }


  @GetMapping("/movies/{id}")
  public ResponseEntity<MovieDTO> getMovies(@PathVariable Long id) {
    Optional<Movie> optionalMovie = movieService.findMovieById(id);

    if (optionalMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(MovieMapper.toMovieDTO(optionalMovie.get()));
  }

  @PostMapping("/movies")
  public ResponseEntity<MovieDTO> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {

    // cia ateina patikrintas
    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedMovie)
                            .toUri()
            )
            .body(MovieMapper.toMovieDTO(savedMovie));
  }

  @GetMapping("/movies/pagination")
  public ResponseEntity<Page<Movie>> getMoviesPage(@RequestParam int page,
                                                   @RequestParam int size,
                                                   @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(movieService.findAllMoviesPage(page, size, sort));
  }

}


