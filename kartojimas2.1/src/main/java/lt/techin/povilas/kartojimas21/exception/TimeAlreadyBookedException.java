package lt.techin.povilas.kartojimas21.exception;

public class TimeAlreadyBookedException extends RuntimeException {
  public TimeAlreadyBookedException(String message) {
    super(message);
  }
}
