package lt.techin.demo.dto;

public class BookDTOno {

  private String author;
  private String title;

  public BookDTOno(String author, String title) {
    this.author = author;
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }
}
