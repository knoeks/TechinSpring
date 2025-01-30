package lt.techin.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books") // Jei to neparašysiu, lentelė bus traktuojama kaip „book“
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL kode, tai yra AUTO_INCREMENT
  private long id; // Duomenų bazėje: BIGINT

  private String title; // Duomenų bazėje: VARCHAR
  private String author; // VARCHAR

  // id nereikia, nes jis generuojamas
  public Book(String title, String author) {
    this.title = title;
    this.author = author;
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

}
