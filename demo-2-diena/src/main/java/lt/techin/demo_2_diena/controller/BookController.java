package lt.techin.demo_2_diena.controller;

import lt.techin.demo_2_diena.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

  private List<Book> books = new ArrayList<>(List.of(new Book("Spring Start Here", "Laurentiu Spilca"),
          new Book("Spring Security In Action", "Laurentiu Spilca")));

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(books);
  }

  @GetMapping("/books/{index}")
  public ResponseEntity<Book> getBook(@PathVariable int index) {
    if (index > books.size() - 1) {
      return ResponseEntity.notFound().build();// build() reikia nurodyti, nes jokio kūno negrąžiname
    }

    return ResponseEntity.ok(books.get(index));
  }

  @PostMapping("/books")
  public ResponseEntity<Book> saveBook(@RequestBody Book book) {
    // Jei pavadinimas arba autorius tušti, grąžinti 400 Bad Request
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    books.add(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(books.size() - 1).toUri()
            )
            .body(book);
  }

  @PutMapping("/books/{index}")
  public ResponseEntity<Book> updateBook(@PathVariable int index, @RequestBody Book book) {
    if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    // Tikriname, ar paduotas indeksas yra
    if (index <= books.size() - 1) { // Patikrinu, ar toks indeksas egzistuoja
      Book bookFromDb = books.get(index);

      bookFromDb.setTitle(book.getTitle());
      bookFromDb.setAuthor(book.getAuthor());

      return ResponseEntity.ok(bookFromDb);
    }

    books.add(book);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(books.size() - 1)
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{index}")
  public ResponseEntity<Void> deleteBook(@PathVariable int index) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses
    if (index > books.size() - 1) {
      return ResponseEntity.notFound().build();
    }

    books.remove(index);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/book") // /books neveiks, nes užimta
  public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
    Optional<Book> foundBook = books.stream()
            .filter(b -> b.getTitle().contains(title))
            .findFirst();

    if (foundBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundBook.get());
  }
}
