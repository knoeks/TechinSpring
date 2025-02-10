package lt.techin.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthorValidator implements ConstraintValidator<Author, String> {

  @Override
  public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
    return author != null && author.matches("^[A-Z][a-z]+$");
  }
}
