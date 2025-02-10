package lt.techin.demo.dto;

public class BookDTOno {

  private long id;
  private String author;
  private String title;

  public BookDTOno(long id, String author, String title) {
    this.id = id;
    this.author = author;
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public long getId() {
    return id;
  }

}
