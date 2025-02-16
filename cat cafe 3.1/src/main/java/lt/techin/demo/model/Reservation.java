package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private Long userId;

  @FutureOrPresent
  @NotNull
  @Column(name = "date_of_reservation")
  private LocalDate dateOfReservation;

  @NotNull
  @Size(max = 50)
  @Column(name = "time_slot")
  private String timeSlot;

  @NotNull
  @Column(name = "num_guests")
  private Integer numGuests;


}
