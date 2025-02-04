package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lt.techin.demo.validation.Author;

import java.util.List;

@Entity
@Table(name = "books") // Jei to neparašysiu, lentelė bus traktuojama kaip „book“
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL kode, tai yra AUTO_INCREMENT
  private long id;

  @NotNull
  @Size(max = 150, message = "Maximum 150 characters")
  // Jau suprantame, jog pattern reikalauja mažiausiai 2 simbolių; jei pridėsime prie
  // @Size min = 2, pjausis su Pattern
  @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase letter, and continue as" +
          " lowercase")
  private String title;

  @Author
  private String author;

  // Galiu ir vaikinėje lentelėje išsaugoti įrašą - review
  @OneToMany(cascade = CascadeType.ALL)

  // Neuždėjus šios anotacijos, Hibernate bandys pats kurti tarpinę lentelę
  @JoinColumn(name = "book_id")
  private List<Review> reviews;

  // Čia CascadeType.ALL nerašyti. Nepakeis vaikinės lentelės - tik kvies
  // egzistuojantį id
  @ManyToMany
  @JoinTable(
          name = "books_categories",
          joinColumns = @JoinColumn(name = "book_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories;

  @OneToOne(cascade = CascadeType.ALL)
  // @JoinColumn(name = "book_detail_id") // Nebūtina rašyti
  // Duomenų bazė supranta, jog bookDetail yra book_detail_id
  private BookDetail bookDetail;

  // id nereikia, nes jis generuojamas
  public Book(String title, String author, List<Review> reviews, List<Category> categories,
              BookDetail bookDetail) {
    this.title = title;
    this.author = author;
    this.reviews = reviews;
    this.categories = categories;
    this.bookDetail = bookDetail;
  }

  // Konstruktorius be argumentų yra reikalingas,
  // tam kad Hibernate galėtų veikti
  public Book() {

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  // getId() reikalingas, kad Jackson galėtu serialize/
  // deserialize id
  public long getId() {
    return id;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public BookDetail getBookDetail() {
    return bookDetail;
  }

  public void setBookDetail(BookDetail bookDetail) {
    this.bookDetail = bookDetail;
  }
}
