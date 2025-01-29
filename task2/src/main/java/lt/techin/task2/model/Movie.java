package lt.techin.task2.model;

public class Movie {
  private String name;
  private String rating;
  private String genre;

  public Movie(String name, String rating, String genre) {
    this.name = name;
    this.rating = rating;
    this.genre = genre;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
