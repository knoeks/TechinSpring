package lt.techin.movie_studio.validation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // sitas priima error type
  @ExceptionHandler(MethodArgumentNotValidException.class)
  // sitam anotacija perduoda errora
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  // sitam anotacija perduoda errora
  public ResponseEntity<Map<String, String>> handleValidationErrors(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getConstraintViolations().forEach(error ->
            errors.put(error.getPropertyPath().toString(), error.getMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

}
