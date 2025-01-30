package com.example.helloTest.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.helloTest.model.Book;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

// jei noriu hotReload tada reikia prideti dependency Spring DevTools


@RestController // Be šios anotacijos, nepasieksite šio controller'io

// sitas nebutinas bet patartinas, nes parodo kad is cia api pasiekiamas
@RequestMapping("/api")
public class BookController {

  // masyvas kuriame saugomi visi objektai
  private List<Book> books = new ArrayList<>(List.of(new Book("Spring Start Here", "Laurentiu Spilca"),
          new Book("Spring Security In Action", "Laurentiu Spilca"))); // Tai tik pvz.


  // ResponseEntity apgaubia List<Book> ir grazina ok + books
  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(books);
  }


  // cia dar paima is linko index
  @GetMapping("/books/{index}")
  public ResponseEntity<Book> getBook(@PathVariable int index) {
    // cia patikrinama ar requestas atitinka realybe
    if (index > books.size() - 1) {
      // cia kadangi neturime ka grazinti ir Response entity reikalauja book objekto
      // tai mums reikia grazinti tuscia kuna
      return ResponseEntity.notFound().build();
    }

    // cia graziname Book jeigu ji egzistuoja
    return ResponseEntity.ok(books.get(index));
  }

  // post metodas
  // grazina body su tuo kintamuoju kuri parasem
  @PostMapping("/books")
  public ResponseEntity<Book> saveBook(@RequestBody Book book) {
    if (book.getName().isEmpty() || book.getAuthor().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    // cia prie musu saraso pridedame dar viena knyga
    books.add(book);

    // cia graziname musu ResponseEntity wrapper + knyga
    // knyga pridedame todel nes standartas kai naujas resursas sukuriamas
    // reikia grazinti sukurta elementa
    return ResponseEntity.created(

                    // grazina vieta to naujo sukurto elemento vieta
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // paima index
                            .path("/{index}")
                            // sugeneruoja naujam irasui index kuris bus paskutinis
                            .buildAndExpand(books.size() - 1)
                            // convertuoja i uri
                            .toUri())
            // cia graziname body su knyga
            .body(book);

  }


  // put metodas
  @PutMapping("/books/{index}")
  public ResponseEntity<Book> updateBook(@PathVariable int index, @RequestBody Book book) {
    if (book.getAuthor().isEmpty() || book.getName().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    // tikriname ar toks index jau egzistuoja
    if (index < books.size()) {

      //sicia pasiima kopija knygos is duomenu bazes
      Book bookFromDb = books.get(index);

      // cia uzsettina duombazes .setAuthor su gauta book.getAuthor()
      bookFromDb.setAuthor(book.getAuthor());
      bookFromDb.setName(book.getName());

      // sitoj vietoj gaziname ok jei patch suveike
      return ResponseEntity.ok(bookFromDb);
    }

    // patch nesuveke todel pridedame dar viena knyga
    books.add(book);

    // grazina sukurtos knygos vieta
    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(books.size() - 1)
                            .toUri())
            .body(book);
  }

  @DeleteMapping("/books/{index}")
  // Void nes jis grazinamas kaip tuscias body
  public ResponseEntity<Void> deleteBook(@PathVariable int index) {
    // jeigu uzklausa su index kuris neegzistuoja tada
    // tada graziname kad uzklausa neegzistuoja
    if (index > books.size() - 1) {
      return ResponseEntity.notFound().build();
    }

    // panaikiname is musu masyvo
    books.remove(index);

    // graziname body be nieko
    return ResponseEntity.noContent().build();
  }
}

