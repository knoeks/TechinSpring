package lt.techin.demo.repository;

import lt.techin.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Tipas klasės, ir jos pirminis raktas <Book, Long>
public interface BookRepository extends JpaRepository<Book, Long> {

  // Derived query
  boolean existsByTitle(String title);

  // Derived query
  Book findByTitle(String title);
}
