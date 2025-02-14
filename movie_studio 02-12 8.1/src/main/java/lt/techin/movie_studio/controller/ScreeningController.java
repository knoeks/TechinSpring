package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import lt.techin.movie_studio.dto.ScreeningDTO;
import lt.techin.movie_studio.dto.ScreeningMapper;
import lt.techin.movie_studio.model.Screening;
import lt.techin.movie_studio.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
  public ResponseEntity<List<ScreeningDTO>> getScreenings() {
    return ResponseEntity.ok(ScreeningMapper.toScreeningsDTOList(screeningService.findAllScreenings()));
  }


  @GetMapping("/screenings/{id}")
  public ResponseEntity<ScreeningDTO> getScreening(@PathVariable Long id) {
    Optional<Screening> optionalScreening = screeningService.findScreeningById(id);
    if (optionalScreening.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(ScreeningMapper.toScreeningDTO(optionalScreening.get()));
  }

  @PostMapping("/screenings")
  public ResponseEntity<ScreeningDTO> saveScreening(@Valid @RequestBody ScreeningDTO screeningDTO) {
    Screening savedScreening = screeningService.saveScreening(ScreeningMapper.toScreening(screeningDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedScreening.getId())
                            .toUri()
            )
            .body(ScreeningMapper.toScreeningDTO(savedScreening));
  }

}


