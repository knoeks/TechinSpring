package lt.techin.demo.controller;

import lt.techin.demo.model.Book;
import lt.techin.demo.model.BookDetail;
import lt.techin.demo.model.Category;
import lt.techin.demo.model.Review;
import lt.techin.demo.security.SecurityConfig;
import lt.techin.demo.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// cia nurodome ka cia tikrinsime
@WebMvcTest(controllers = BookController.class)
// reikia importuoti Spring Security, nes jame yra visa svarbi
// login informacija
@Import(SecurityConfig.class)
public class BookControllerTest {

    // kadangi konstruktorius nebus naudojamas nes testai yra
    // izoliuoti nuo kitu komponentu, todel mes galime
    // @autowired ant field
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    // Happy path testas, ty patikrint ar teisingai issiuntus duomenis
    // ar viskas veiks normaliai

    @Test
    // sukuria autentifikuota useri kad galetume
    // prieiti prie endpointu
    @WithMockUser
        // 1) testai neturetu buti su public aksesoriais nes
        // vis tiek jie lokalizuoti tik cia

        // 2) deskriptyvus pavadinimas sudarytas is daliu:
        // getBooks - controllerio metodo pavadinimas_
        // "kai nutiks kazkas" dalis_
        // "tada turi nutikt tai" dalis
    void getBooks_whenFindAll_thenReturnAllAnd200() throws Exception {
        // given dalis
        // reikia sukurt objektus Book ir tada su jomis testuoti
        // ar viskas veikia kaip turetu
        Book book1 = new Book("Food for Thought: Essays and Ruminations",
                "Alton Brown",
                List.of(),
                List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
                new BookDetail("978-1668064214", 304, "English")
        );

        Book book2 = new Book("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century",
                "Ian Mortimer",
                List.of(new Review("Great book. An introduction to Medieval England"), new Review("I don't think you can write a WORSE book!!!")),
                List.of(new Category("History"), new Category("Nonfiction")),
                new BookDetail("978-1439112908", 368, "English")
        );

        List<Book> books = List.of(book1, book2);

        // cia paduodame musu duomenis i duombaze
        // BDDMockito
        given(bookService.findAllBooks()).willReturn(books);

        // rekomenduojami komentarai
        // when ( kai pradesim sita darba):
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                // then (tikesimes kad ivyks):
                .andExpect(status().isOk())
                // cia patikriname ar atgaunami duomenys taip pat du
                .andExpect(jsonPath("length()").value(2))

                // check first book regular fields
                .andExpect(jsonPath("[0].id").exists())
                .andExpect(jsonPath("[0].title").value("Food for Thought: Essays and Ruminations"))
                .andExpect(jsonPath("[0].author").value("Alton Brown"))

                // cia pasiziuret ar tai masyvas
                //check first book reviews
                .andExpect(jsonPath("[0].reviews").isArray())
                // patikrina ar jo dydis 0
                .andExpect(jsonPath("[0].reviews", Matchers.hasSize(0)))

                // check first book's categories
                .andExpect(jsonPath("[0].categories").isArray())
                .andExpect(jsonPath("[0].categories", Matchers.hasSize(2)))
                .andExpect(jsonPath("[0].categories.[0].id").exists())
                .andExpect(jsonPath("[0].categories.[0].name").value("Biographies & Memoirs"))
                .andExpect(jsonPath("[0].categories.[1].id").exists())
                .andExpect(jsonPath("[0].categories.[1].name").value("Arts & Literature"))

                // check first book bookDetails
                .andExpect(jsonPath("[0].bookDetail.id").exists())
                .andExpect(jsonPath("[0].bookDetail.isbn").value("978-1668064214"))
                .andExpect(jsonPath("[0].bookDetail.pageCount").value(304))
                .andExpect(jsonPath("[0].bookDetail.language").value("English"))
                // TODO: taip pat padaryt antra knyga (grusti i ta pati metoda)
                .andExpect(jsonPath("[1].id").exists())
                // testai yra tiesiog as -> ;-;
                .andExpect(jsonPath("[1].title").value("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century"))
                .andExpect(jsonPath("[1].author").value("Ian Mortimer"))
                .andExpect(jsonPath("[1].reviews").isArray())
                .andExpect(jsonPath("[1].reviews", Matchers.hasSize(2)))

                .andExpect(jsonPath("[1].reviews.[0].id").exists())
                .andExpect(jsonPath("[1].reviews.[0].comment").value("Great book. An introduction to Medieval England"))
                .andExpect(jsonPath("[1].reviews.[1].id").exists())
                .andExpect(jsonPath("[1].reviews.[1].comment").value("I don't think you can write a WORSE book!!!"))

                .andExpect(jsonPath("[1].categories.[0].id").exists())
                .andExpect(jsonPath("[1].categories.[0].name").value("History"))
                .andExpect(jsonPath("[1].categories.[1].id").exists())
                .andExpect(jsonPath("[1].categories.[1].name").value("Nonfiction"))
                .andExpect(jsonPath("[1].bookDetail.id").exists())
                .andExpect(jsonPath("[1].bookDetail.isbn").value("978-1439112908"))
                .andExpect(jsonPath("[1].bookDetail.pageCount").value(368))
                .andExpect(jsonPath("[1].bookDetail.language").value("English"));

        // galiausiai reikia patikrinti kad metodas buvo iskviestas tiek kartu kiek jis buvo
        // kvieciamas (buvo kviesta 1 karta todel: 1 karta kviestas)
        Mockito.verify(bookService, times(1)).findAllBooks();
    }

    // padarykime unhappy path:
    @Test
    void getBooks_whenFindAllUnauthenticated_thenRespond401() throws Exception {
        // given (copy paste from previous)
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

        //butina patikrinti kad tikrai nei karto nebuvo iskviestas metodas
        Mockito.verify(bookService, times(0)).findAllBooks();

    }


}
