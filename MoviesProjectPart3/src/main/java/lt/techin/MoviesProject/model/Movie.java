package lt.techin.MoviesProject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "movies")

public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long Id;


  // checks for null
  // @NotNull
  // checks for null or empty
//  @NotEmpty
  // checks for null, empty, whitespace only
  @NotBlank
  @Size(max = 50)
  private String title;

  @NotBlank
  @Size(max = 60)
  private String director;


  public Movie(String title, String director) {
    this.title = title;
    this.director = director;
  }


  public Movie() {

  }

  public long getId() {
    return Id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }
}
