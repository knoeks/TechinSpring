package lt.techin.movie_studio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  @Size(max = 3)
  private String hall;

  @Future
  @NotNull
  private LocalDateTime time;

  public Screening(long id, String hall, LocalDateTime time) {
    this.id = id;
    this.hall = hall;
    this.time = time;
  }

  public Screening() {

  }

  public long getId() {
    return id;
  }

  public String getHall() {
    return hall;
  }

  public void setHall(String hall) {
    this.hall = hall;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }
}
