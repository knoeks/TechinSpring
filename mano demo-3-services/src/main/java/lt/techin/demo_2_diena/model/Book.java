package lt.techin.demo_2_diena.model;

import jakarta.persistence.*;

// sicia reikia sutvarkyti sasaja su duombaze kad
// jackson(??) zinotu kaip kurt uzklausas


// cia nurodome kad jis entity kuris sukurs
// JSON faila is kurio bus Jackson konvertuos i Stringa tam
// kad java galetu apdoroti tuos duomenis

@Entity
// cia reikia nurodyti duomabazes lenteles pavadinima nes by default
// naudojamas klases pavadinimas
@Table(name = "books")
public class Book {

  // cia nurodome kad tai yra ID lenteles
  @Id
  // cia nurodome kaip generuojamas raktas duombazej
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // duomenu bazej BIGINT

  private String title; // duomenu bazej VARCHAR
  private String author; // VARCHAR

  // id nereikia, nes jis sugeneruojamas
  // plius JPA naudoja tuscia konstruktoriu, kad
  // sukurtu sita objekta
  // bet sake kad kazkokiais atvejais
  // naudosim sita konstruktoriu

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

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

  // si situo ID jackson serialize/deserialize daro
  // todel jis butinas
  public long getId() {
    return id;
  }
}

// cia jau sukurtas pilnas veikiantis modelis
