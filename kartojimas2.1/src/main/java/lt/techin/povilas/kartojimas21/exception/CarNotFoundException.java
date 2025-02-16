package lt.techin.povilas.kartojimas21.exception;

public class CarNotFoundException extends RuntimeException {
  public CarNotFoundException(String message) {
    super(message);
  }
}
