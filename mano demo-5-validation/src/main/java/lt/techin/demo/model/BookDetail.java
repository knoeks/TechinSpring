package lt.techin.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books_details")
public class BookDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String isbn;
  private int pageCount;
  private String language;

  public BookDetail(String isbn, int pageCount, String language) {
    this.isbn = isbn;
    this.pageCount = pageCount;
    this.language = language;
  }

  public BookDetail() {
  }

  public long getId() {
    return id;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
