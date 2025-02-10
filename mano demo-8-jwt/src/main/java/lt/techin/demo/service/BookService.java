package lt.techin.demo.service;

import lt.techin.demo.model.Book;
import lt.techin.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
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

  public List<Book> findAllBooksByTitleContaining(String title) {
    // Calls a derived query
    return bookRepository.findAllByTitleContaining(title);
  }

  public List<Book> findAllBooksByAuthor(String author) {
    return bookRepository.findAllByAuthor(author);
  }

  public Page<Book> findAllBooksPage(int page, int size, String sort) {
    if (sort == null) {
      Pageable pageable = PageRequest.of(page, size);

      return bookRepository.findAll(pageable);
    }

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return bookRepository.findAll(pageable);
  }
}
