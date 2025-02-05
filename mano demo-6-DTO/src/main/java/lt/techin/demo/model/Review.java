package lt.techin.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private String comment;

  public Review(String comment) {
    this.comment = comment;
  }

  public Review() {
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getId() {
    return id;
  }

}
