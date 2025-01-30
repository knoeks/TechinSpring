package lt.techin.demo_2_diena.controller;

import lt.techin.demo_2_diena.model.Book;
import lt.techin.demo_2_diena.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

  // @autowired // field injection (nerekomenduojamas)
  private final BookService bookService;

  // constructor injection (rekomenduojamas)
  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBook(@PathVariable long id) {
    Optional<Book> optionalBook = bookService.findBookById(id);

    if (optionalBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // reikia neuzmirst .get() panaudot nes ten optional yra
    return ResponseEntity.ok(optionalBook.get());
  }

  @PostMapping("/books")
  public ResponseEntity<?> saveBook(@RequestBody Book book) {
    if (book == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not initialized");
    }

    if (book.getTitle() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book title not set.");
    }

    if (book.getAuthor() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book author not set.");
    }

    if (book.getTitle().isEmpty()) {
      // cia standartinis neinformatyvus returnas
      //return ResponseEntity.badRequest().build();

      // kai badRequest returninam tai reiktu pridet daugiau info
      // prie return
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title cannot be empty");
      // tik cia returnas grazins stringa todel reiktu pakeist
      // Book -> ? (generic)
    }

    if (book.getAuthor().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("author cannot be empty");
    }

    // jeigu parkeliavo legalus objektas
    // tada galima siusti i duombaze
    Book savedBook = bookService.saveBook(book);

    // cia atsiuncia sukurto objekto duombazej nuoroda
    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedBook.getId()).toUri()
            )
            .body(book);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
    if (book.getTitle().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title cannot be empty.");
    }

    if (book.getAuthor().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author cannot be empty.");
    }

    // patikriname ar tokia knyga jau duombazej egzistuoja
    if (bookService.existsBookById(id)) {
      // paima jau egzistuojancia knyga
      Book bookFromDb = bookService.findBookById(id).get();

      // ir tada sicia jos laukus pakeicia
      bookFromDb.setTitle(book.getTitle());
      bookFromDb.setAuthor(book.getAuthor());

      // cia issaugome nauja apdorota knyga
      return ResponseEntity.ok(bookService.saveBook(bookFromDb));
    }

    // jeigu neegzistuoja tokia knyga tada mes sukuriame nauja knyga
    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/api/books/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable int id) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses

    if (!bookService.existsBookById(id)) {
      return ResponseEntity.notFound().build();
    }

    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/books/search") //books neveiks, nes užimta
  public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
    Optional<Book> searchedBooks = bookService.findBookByTitle(title);

    if (searchedBooks.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(searchedBooks.get());
  }
}
