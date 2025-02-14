package lt.techin.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.demo.model.Book;
import lt.techin.demo.model.BookDetail;
import lt.techin.demo.model.Category;
import lt.techin.demo.model.Review;
import lt.techin.demo.security.SecurityConfig;
import lt.techin.demo.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Vienas sluoksnis - controller, Spring MVC
@WebMvcTest(controllers = BookController.class)

// Reikia nurodyti, nes pas mus aplikacijoje yra Spring Security
@Import(SecurityConfig.class)
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BookService bookService;

  // Happy path test
  @Test
  // Sukuria netikrą autentifikuotą vartotoją, tam kad galėtų veikti Spring Security
  @WithMockUser
  void getBooks_whenFindAll_thenReturnAllAnd200() throws Exception {
    // Galima kurti ir tuščia Book, ir vėliau pildyti su setter'iais
    // given
    Book book1 = new Book("Food for Thought: Essays and Ruminations",
            "Alton Brown",
            List.of(),
            List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
            new BookDetail("978-1668064214", 304, "English"));

    Book book2 = new Book("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century",
            "Ian Mortimer",
            List.of(new Review("Great book. An introduction to Medieval England"), new Review("I don't think you can write a WORSE book!!!")),
            List.of(new Category("History"), new Category("Nonfiction")),
            new BookDetail("978-1439112908", 368, "English"));

    List<Book> books = List.of(book1, book2);

    // Paduodami duomenys į mūsų „duomenų bazę“ - tas bus grąžinama metodo
    given(bookService.findAllBooks()).willReturn(books);

    // Šie komentarai nėra reikalingi, bet rekomenduojami
    // when
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(2))

            // Check first book's regular fields
            // Testuojame ar id laukas egzistuoja; žinoma, jis nėra inkrementuojamas, nes nėra
            // sąsajos su duomenų baze
            .andExpect(jsonPath("[0].id").exists())
            .andExpect(jsonPath("[0].title").value("Food for Thought: Essays and Ruminations"))
            .andExpect(jsonPath("[0].author").value("Alton Brown"))

            // Check first book's reviews
            .andExpect(jsonPath("[0].reviews").isArray())
            .andExpect(jsonPath("[0].reviews", Matchers.hasSize(0)))

            // Check first book's categories
            .andExpect(jsonPath("[0].categories").isArray())
            .andExpect(jsonPath("[0].categories", Matchers.hasSize(2)))
            .andExpect(jsonPath("[0].categories.[0].id").exists())
            .andExpect(jsonPath("[0].categories.[0].name").value("Biographies & Memoirs"))
            .andExpect(jsonPath("[0].categories.[1].id").exists())
            .andExpect(jsonPath("[0].categories.[1].name").value("Arts & Literature"))

            // Check first book's bookDetail
            .andExpect(jsonPath("[0].bookDetail.id").exists())
            .andExpect(jsonPath("[0].bookDetail.isbn").value("978-1668064214"))
            .andExpect(jsonPath("[0].bookDetail.pageCount").value(304))
            .andExpect(jsonPath("[0].bookDetail.language").value("English"));

    // TODO: patikrinti antros knygos laukus...

    // Užtikriname, jog findAllBooks buvo kviestat tik vieną kartą
    Mockito.verify(bookService, times(1)).findAllBooks();
  }

  // Unhappy path
  @Test
  void getBooks_whenFindAllUnauthenticated_thenRespond401() throws Exception {
    // given
    Book book1 = new Book("Food for Thought: Essays and Ruminations",
            "Alton Brown",
            List.of(),
            List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
            new BookDetail("978-1668064214", 304, "English"));

    Book book2 = new Book("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century",
            "Ian Mortimer",
            List.of(new Review("Great book. An introduction to Medieval England"), new Review("I don't think you can write a WORSE book!!!")),
            List.of(new Category("History"), new Category("Nonfiction")),
            new BookDetail("978-1439112908", 368, "English"));

    List<Book> books = List.of(book1, book2);

    given(bookService.findAllBooks()).willReturn(books);

    // when
    mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
            // then
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$").doesNotExist());

    // Patikrinti, ar findAllBooks NEBUVO kviestas
    Mockito.verify(bookService, times(0)).findAllBooks();
  }

  // TODO: unhappy path tests: getBooks - what if user is authenticated, but the returned list
  //  is empty?

  // Happy path
  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_ADMIN")
  void addBook_whenAdminAddBook_thenReturnAnd201() throws Exception {
    // given
    Book book = new Book("Foodforthoughtessaysandruminations",
            "Alton Brown",
            List.of(),
            List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
            new BookDetail("978-1668064214", 304, "English"));

    BDDMockito.given(bookService.saveBook(ArgumentMatchers.any(Book.class))).willReturn(book);

    ObjectMapper objectMapper = new ObjectMapper();

    // when
    mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(book)))
            // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("title").value("Foodforthoughtessaysandruminations"));
    // Check all other fields too...

    Mockito.verify(bookService, times(1)).saveBook(ArgumentMatchers.any(Book.class));
  }

  // TODO: unhappy path methods: addBook - what happens if user is Admin, but title does not
  //  match validation (can be many tests)
  // TODO: What happens if unauthenticated user tries to call this endpoint?
}
