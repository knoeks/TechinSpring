package lt.techin.povilas.kartojimas21.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "rental_start")
  private LocalDate rentalStart;

  @Column(name = "rental_end")
  private LocalDate rentalEnd;

  private BigDecimal price;

  public Rental(Long id, LocalDate rentalStart, LocalDate rentalEnd, BigDecimal price) {
    this.id = id;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
  }

  public Rental() {
  }

  public Long getId() {
    return id;
  }

  public LocalDate getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDate rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDate getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDate rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


}
