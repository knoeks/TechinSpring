package lt.techin.movie_studio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String hall;
  @Column(name = "screening_time")
  private LocalDateTime screeningTime;
  public Screening(long id, String hall, LocalDateTime screeningTime) {
    this.id = id;
    this.hall = hall;
    this.screeningTime = screeningTime;
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

  public LocalDateTime getScreeningTime() {
    return screeningTime;
  }

  public void setScreeningTime(LocalDateTime screeningTime) {
    this.screeningTime = screeningTime;
  }
}
