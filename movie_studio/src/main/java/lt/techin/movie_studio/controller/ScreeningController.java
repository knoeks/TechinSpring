package lt.techin.movie_studio.controller;

import lt.techin.movie_studio.model.Screening;
import lt.techin.movie_studio.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ScreeningController {
  private final ScreeningService screeningService;

  @Autowired
  public ScreeningController(ScreeningService screeningService) {
    this.screeningService = screeningService;
  }

  @GetMapping("/screenings")
  public ResponseEntity<List<Screening>> getScreenings() {
    return ResponseEntity.ok(screeningService.findAllScreenings());
  }


  @GetMapping("/screenings/{id}")
  public ResponseEntity<Screening> getScreening(@PathVariable Long id) {
    Optional<Screening> optionalScreening = screeningService.findScreeningById(id);
    if (optionalScreening.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optionalScreening.get());
  }

  @PostMapping("/screenings")
  public ResponseEntity<Screening> saveScreening(@RequestBody Screening screening) {
    Screening savedScreening = screeningService.saveScreening(screening);
  }

}


