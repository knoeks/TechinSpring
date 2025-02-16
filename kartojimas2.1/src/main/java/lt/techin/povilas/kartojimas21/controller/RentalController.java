package lt.techin.povilas.kartojimas21.controller;

import lt.techin.povilas.kartojimas21.model.Rental;
import lt.techin.povilas.kartojimas21.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/api")
public class RentalController {
  private final RentalService rentalService;

  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @PostMapping("/rentals")
  public ResponseEntity<Rental> addRental(@RequestBody Rental rental) {
    return ResponseEntity.ok(rentalService.saveRental(rental));
  }

  @PostMapping("/rentals/return/{rentalId}")
  public ResponseEntity<Rental> returnRental(@PathVariable Long id, Authentication authentication) {
    Optional<Rental> rentalFromDb = rentalService.findRentalById(id);

    if (rentalFromDb.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    // cannot return a car that is already available
    if (rentalFromDb.get().getCar().getStatus().equals("AVAILABLE")) {
      return ResponseEntity.badRequest().build();
    }

    if (!authentication.getName().equals(rentalFromDb.get().getUser().getUsername())) {
      ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(rentalFromDb.get());
  }

  @GetMapping("/rentals/my")
  public ResponseEntity<List<Rental>> getUserRentals(Authentication authentication) {
    List<Rental> rentalList = rentalService.findAllRentalsByCurrentlyActive().stream().filter(item ->
            item.getUser().getUsername().equals(authentication.getName()))
            .toList();
    return ResponseEntity.ok(rentalList);
  }

  @GetMapping("/rentals/history")
  public ResponseEntity<List<Rental>> getRentalHistory() {
    return ResponseEntity.ok(rentalService.findAllRentals());
  }
}
