package lt.techin.demo.dto;

import lt.techin.demo.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

  public static List<BookDTO> toBookDTOList(List<Book> books) {
    List<BookDTO> result = books.stream()
            .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getReviews(), book.getCategories(), book.getBookDetail()))
            .toList();

    return result;
  }

  public static BookDTO toBookDTO(Book book) {
    return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getReviews(), book.getCategories(), book.getBookDetail());
  }

  public static Book toBook(BookDTO bookDTO) {
    Book book = new Book();
    book.setTitle(bookDTO.title());
    book.setAuthor(bookDTO.author());
    book.setReviews(bookDTO.reviews());
    book.setCategories(bookDTO.categories());
    book.setBookDetail(bookDTO.bookDetail());

    return book;
  }

  public static void updateBookFromDTO(Book book, BookDTO bookDTO) {
    book.setTitle(bookDTO.title());
    book.setAuthor(bookDTO.author());
    book.setReviews(bookDTO.reviews());
    book.setCategories(bookDTO.categories());
  }
}
