package lt.techin.povilas.kartojimas21.service;

import lt.techin.povilas.kartojimas21.exception.CarNotAvailableException;
import lt.techin.povilas.kartojimas21.exception.TimeAlreadyBookedException;
import lt.techin.povilas.kartojimas21.exception.TooShortRentalDurationException;
import lt.techin.povilas.kartojimas21.model.Rental;
import lt.techin.povilas.kartojimas21.repository.RentalRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
  private final RentalRepository rentalRepository;

  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  public List<Rental> findAllRentalsByCurrentlyActive() {
    return rentalRepository.findAll().stream().filter(item ->
            item.getRentalStart().isBefore(LocalDate.now())
                    && item.getRentalEnd().isAfter(LocalDate.now()))
            .toList();
  }

  public Rental saveRental(Rental rental) {
    LocalDate rentalStart = rental.getRentalStart();
    LocalDate rentalEnd = rental.getRentalEnd();
    Long days = ChronoUnit.DAYS.between(rentalStart, rentalEnd);
    BigDecimal baseRate = new BigDecimal("129.99");

    if (!rental.getCar().getStatus().equals("AVAILABLE")) {
      throw new CarNotAvailableException("Car is not available for rental.");
    }

    // check if user doesn't have a car booked already in the same date
    if (2 < rental.getUser().getRentals().stream().filter(item ->
                    rentalStart.isBefore(item.getRentalEnd())
                            || rentalEnd.isAfter(item.getRentalStart()))
            .count()
    ) {
      throw new TimeAlreadyBookedException("Cannot rent 2 cars in the same period of time");
    }

    if (days < 1) {
      throw new TooShortRentalDurationException("Rental duration cannot be lower than 1 day");
    }

    rental.setPrice(baseRate.multiply(new BigDecimal(days)));

    return rentalRepository.save(rental);
  }

  public Optional<Rental> findRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  public List<Rental> findAllRentals() {
    return rentalRepository.findAll();
  }
}
