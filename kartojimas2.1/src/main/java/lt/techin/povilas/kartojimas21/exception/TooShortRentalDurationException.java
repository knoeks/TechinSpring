package lt.techin.povilas.kartojimas21.exception;

public class TooShortRentalDurationException extends RuntimeException {
  public TooShortRentalDurationException(String message) {
    super(message);
  }
}
