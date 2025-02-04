package xxx.cya.BitCoinSaveTransfers.webSocketLearn.model;

public class Message {
  private String from;
  private String text;

  // Default constructor
  public Message() {
  }

  public Message(String from, String text) {
    this.from = from;
    this.text = text;
  }

  // Getters and setters
  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
