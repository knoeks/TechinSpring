package lt.techin.povilas.kartojimas21.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 2, max = 50)
  private String brand;

  @NotBlank
  @Size(min = 2, max = 50)
  private String model;

  @NotNull
  @Past
  private Integer year;

  @NotBlank
  private String status;

  @OneToMany
  @JoinColumn(name = "car_id")
  private List<Rental> rentals;

  public Car() {
  }

  public Car(Long id, String brand, String model, Integer year, String status, List<Rental> rentals) {
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.status = status;
    this.rentals = rentals;
  }

  public Long getId() {
    return id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public void setRentals(List<Rental> rentals) {
    this.rentals = rentals;
  }
}
