package lt.techin.task2.controller;


import lt.techin.task2.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

  // String name, String rating, String genre
  private final List<Movie> movies = new ArrayList<Movie>(
          List.of(new Movie("juju", "18+", "fantasy"),
                  new Movie("No Man", "13", "drama"),
                  new Movie("Funny life", "8", "comedy"),
                  new Movie("Fast and foodios", "18+", "thriller"),
                  new Movie("Hobbit", "13", "fantasy")
          )
  );

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovie(@PathVariable int index) {
    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movies.get(index));
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
    if (movie.getGenre().isEmpty() || movie.getName().isEmpty() || movie.getRating().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    // cia galima papildyti ArrayList nes jau "tvarkingi" duomenys cia liks
    movies.add(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(movies.size() - 1)
                            .toUri())
            .body(movie);
  }

}
