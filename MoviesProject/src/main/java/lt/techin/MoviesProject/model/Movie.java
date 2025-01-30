package lt.techin.MoviesProject.model;


import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long Id;

  private String title;
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
