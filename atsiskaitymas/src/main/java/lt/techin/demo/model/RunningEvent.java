package lt.techin.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "running_events")
public class RunningEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "calendar_date")
  private LocalDate calendarDate;

  private String location;

  @Column(name = "max_participants")
  private Integer maxParticipants;

  @OneToMany
  @JoinColumn(name = "running_event_id")
  private List<Registration> registrations;

  public RunningEvent(Long id, String name, LocalDate calendarDate, String location, Integer maxParticipants, List<Registration> registrations) {
    this.id = id;
    this.name = name;
    this.calendarDate = calendarDate;
    this.location = location;
    this.maxParticipants = maxParticipants;
    this.registrations = registrations;
  }

  public RunningEvent() {

  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCalendarDate() {
    return calendarDate;
  }

  public void setCalendarDate(LocalDate calendarDate) {
    this.calendarDate = calendarDate;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Integer getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(Integer maxParticipants) {
    this.maxParticipants = maxParticipants;
  }

  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }
}
