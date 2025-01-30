package lt.techin.demo_2_diena.service;

import lt.techin.demo_2_diena.model.Book;
import lt.techin.demo_2_diena.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  // sitas sujungia su atitinkamu autowired
  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  // istraukia is susietos repozitorijos visas knygas
  // ir jas grazina
  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> findBookById(long id) {
    return bookRepository.findById(id);
  }

  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public boolean existsBookById(long id) {
    return bookRepository.existsById(id);
  }

  public void deleteBookById(long id) {
    bookRepository.deleteById(id);
  }

//  public List<Book> findAllBooksByTitle(String title) {
//    return bookRepository.findAllBooks().stream().filter(item -> item.getTitle().contains(title)).toList();
//  }


  public List<Book> findAllBooksByTitle(String title) {
    return bookRepository.findAllByTitle(title);
  }

  public Optional<Book> findBookByTitle(String title) {
    return bookRepository.findByTitle(title);
  }
}
