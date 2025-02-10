package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lt.techin.demo.dto.BookDTO;
import lt.techin.demo.dto.BookDTOno;
import lt.techin.demo.dto.BookMapper;
import lt.techin.demo.model.Book;
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
  public ResponseEntity<List<BookDTO>> getBooks() {
    return ResponseEntity.ok(BookMapper.toBookDTOList(bookService.findAllBooks()));
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<BookDTO> getBook(@PathVariable @Min(1) long id) {
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

    return ResponseEntity.ok(BookMapper.toBookDTO(foundBook.get()));
  }

  @PostMapping("/books")
  // @Valid anotacija būtina; dėti ant POST ir PUT controllerių
  public ResponseEntity<?> addBook(@Valid @RequestBody BookDTO bookDTO) {

//    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
//      Map<String, String> result = new HashMap<>();
//      result.put("title", "Cannot be empty");
//
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
//    }

    Book savedBook = bookService.saveBook(BookMapper.toBook(bookDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(BookMapper.toBookDTO(savedBook));
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody BookDTO bookDTO) {
//    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
//    }

    if (bookService.existsBookById(id)) {
      Book bookFromDb = bookService.findBookById(id).get();

//      Not very good
//      bookFromDb.setTitle(bookDTO.title());
//      bookFromDb.setAuthor(bookDTO.author());
//      bookFromDb.setReviews(bookDTO.reviews());
//      bookFromDb.setCategories(bookDTO.categories());

      BookMapper.updateBookFromDTO(bookFromDb, bookDTO);

      bookService.saveBook(bookFromDb);

      return ResponseEntity.ok(bookDTO);
    }

    Book savedBook = bookService.saveBook(BookMapper.toBook(bookDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo; nurodome pilną kelią
                            .replacePath("/api/books/{id}")
                            .buildAndExpand(savedBook.getId())
                            .toUri())
            .body(bookDTO);
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

