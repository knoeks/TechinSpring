package lt.techin.povilas.kartojimas21.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "rental_start")
  @Future
  private LocalDate rentalStart;

  @Column(name = "rental_end")
  @Future
  private LocalDate rentalEnd;
  @NotNull
  @Positive
  private BigDecimal price;

  @ManyToOne
  private Car car;

  @ManyToOne
  private User user;


  public Rental(Long id, LocalDate rentalStart, LocalDate rentalEnd, BigDecimal price, Car car, User user) {
    this.id = id;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
    this.car = car;
    this.user = user;
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

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
