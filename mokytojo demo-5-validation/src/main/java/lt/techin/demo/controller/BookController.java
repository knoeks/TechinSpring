package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Book;
import lt.techin.demo.repository.BookRepository;
import lt.techin.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BookController {

  // @Autowired // field injection (not recommended)
  private final BookService bookService;

  @Autowired // constructor injection (very good)
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<?> getBook(@PathVariable @Min(1) long id) {
    // Gal geriau validuoti su if, vietoj @Min
//    if (id < 1) {
//      Map<String, String> response = new HashMap<>();
//      response.put("id", "Cannot be lower than 1");
//
//      return ResponseEntity.badRequest().body(response);
//    }

    Optional<Book> foundBook = bookService.findBookById(id);

    if (foundBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundBook.get());
  }

  @PostMapping("/books")
  // @Valid anotacija būtina; dėti ant POST ir PUT controllerių
  public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {

//    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
//      Map<String, String> result = new HashMap<>();
//      result.put("title", "Cannot be empty");
//
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
//    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(savedBook);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    if (bookService.existsBookById(id)) {
      Book bookFromDb = bookService.findBookById(id).get();

      bookFromDb.setTitle(book.getTitle());
      bookFromDb.setAuthor(book.getAuthor());
      bookFromDb.setReviews(book.getReviews());
      bookFromDb.setCategories(book.getCategories());

      return ResponseEntity.ok(bookService.saveBook(bookFromDb));
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo; nurodome pilną kelią
                            .replacePath("/api/books/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable long id) {
    if (!bookService.existsBookById(id)) {
      return ResponseEntity.notFound().build();
    }

    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/books/search/by-title")
  public ResponseEntity<List<Book>> getBooksByTitleContaining(@RequestParam @Size(min = 2) String title) {
    return ResponseEntity.ok(bookService.findAllBooksByTitleContaining(title));
  }

  @GetMapping("/books/search/by-author")
  public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
    return ResponseEntity.ok(bookService.findAllBooksByAuthor(author));
  }

  // Pagination
  @GetMapping("/books/pagination")
  public ResponseEntity<Page<Book>> getBooksPage(@RequestParam int page,
                                                 @RequestParam int size,
                                                 @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(bookService.findAllBooksPage(page, size, sort));
  }
}

