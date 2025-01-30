package lt.techin.demo.service;

import lt.techin.demo.model.Book;
import lt.techin.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;
  
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public boolean existsBookById(long id) {
    return bookRepository.existsById(id);
  }

  public Optional<Book> findBookById(long id) {
    return bookRepository.findById(id);
  }

  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public void deleteBookById(long id) {
    bookRepository.deleteById(id);
  }

  public boolean existsBookByTitle(String title) {
    // Call custom derived query
    return bookRepository.existsByTitle(title);
  }

  public Book findBookByTitle(String title) {
    return bookRepository.findByTitle(title);
  }
}
