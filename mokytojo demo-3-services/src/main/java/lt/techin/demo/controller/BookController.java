package lt.techin.demo.controller;

import lt.techin.demo.model.Book;
import lt.techin.demo.repository.BookRepository;
import lt.techin.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

  // @Autowired // field injection (not recommended)
  private final BookService bookService;

  // constructor injection (very good)
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBook(@PathVariable long id) {
//    if (id > books.size() - 1) {
//      return ResponseEntity.notFound().build(); // Build reikia nurodyti, nes jokio kūno negrąžiname
//    }
    Optional<Book> foundBook = bookService.findBookById(id);

    if (foundBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundBook.get());
  }

  @PostMapping("/books")
  // Klaustukas - būtinas, nes grąžinamas tipas gali būti
  // Book arba String
  public ResponseEntity<?> saveBook(@RequestBody Book book) {
    // Jei pavadinimas arba autorius turšti, grąžinti 400 Bad Request
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
//      return ResponseEntity.badRequest().build();
      // Grąžiname ir String, tam kad klientui būdų aiškiau,
      // kas įvyko blogai
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
      // return ResponseEntity.badRequest().build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    if (bookService.existsBookById(id)) {
      Book bookFromDb = bookService.findBookById(id).get();

      bookFromDb.setTitle(book.getTitle());
      bookFromDb.setAuthor(book.getAuthor());

      return ResponseEntity.ok(bookService.saveBook(bookFromDb));
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo
                            .replacePath("/api/books/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable long id) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses
    //    if (index > books.size() - 1) {
    //
    // }
    if (!bookService.existsBookById(id)) {
      return ResponseEntity.notFound().build();
    }

    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }

  // Toks endpoint geriau
  @GetMapping("/books/search")
  public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
//    Optional<Book> foundBook = bookService.findAllBooks().stream()
//            .filter(b -> bookService.existsBookByTitle(title))
//            .findFirst();

    // Reikia koreguoti

    for (Book book : bookService.findAllBooks()) {
      if (!bookService.existsBookByTitle(title)) {
        return ResponseEntity.notFound().build();
      }
    }

    return ResponseEntity.ok(bookService.findBookByTitle(title));
  }
}

