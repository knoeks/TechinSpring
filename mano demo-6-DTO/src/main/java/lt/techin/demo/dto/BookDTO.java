package lt.techin.demo.dto;

import lt.techin.demo.model.BookDetail;
import lt.techin.demo.model.Category;
import lt.techin.demo.model.Review;

import java.util.List;

public record BookDTO(Long id,
                      String title,
                      String author,
                      List<Review> reviews,
                      List<Category> categories,
                      BookDetail bookDetail) {
}
