package lt.techin.demo.repository;

import lt.techin.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Tipas klasės, ir jos pirminis raktas <Book, Long>
public interface BookRepository extends JpaRepository<Book, Long> {

  // Derived query
  List<Book> findAllByTitleContaining(String title);

  // Šis metodas ras knygas, jei pavadinimas yra vienas prie vieno
  List<Book> findAllByTitle(String title);

  // ?1 simbolizuoja pirmą metodo parametrą, t. y. author
  @NativeQuery(value = "SELECT * FROM books WHERE author = ?1")
  List<Book> findAllByAuthor(String author);

  // JPQL query; veikia taip pat, kaip @NativeQuery
  // Sintaksė remiasi būtent Java kodu
  // @Query("select b from Book b where b.author = ?1")
  // List<Book> findAllByAuthor(String author);
}
