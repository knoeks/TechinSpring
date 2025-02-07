package lt.techin.movie_studio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonValidator implements ConstraintValidator<Person, String> {


  @Override
  public boolean isValid(String person, ConstraintValidatorContext constraintValidatorContext) {
    return person != null && person.matches("\\b([A-ZÀ-ÿ][-,a-z. ']+ *)+");
  }
}
