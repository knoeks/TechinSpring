package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.BookDetail;
import lt.techin.demo.model.Category;
import lt.techin.demo.model.Review;
import lt.techin.demo.validation.Author;

import java.util.List;

public record BookDTO(long id,
                      @NotNull
                      @Size(max = 150, message = "Maximum 150 characters")
                      // Jau suprantame, jog pattern reikalauja mažiausiai 2 simbolių; jei pridėsime prie
                      // @Size min = 2, pjausis su Pattern
                      @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase letter, and continue as" +
                              " lowercase")
                      String title,
                      @Author
                      String author,
                      List<Review> reviews,
                      List<Category> categories,
                      BookDetail bookDetail) {

}
