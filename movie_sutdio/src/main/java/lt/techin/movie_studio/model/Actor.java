package lt.techin.movie_studio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lt.techin.movie_studio.validation.Person;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 60)
  @Person
  private String name;
  @Positive
  private Short age;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Short getAge() {
    return age;
  }

  public void setAge(Short age) {
    this.age = age;
  }
}
