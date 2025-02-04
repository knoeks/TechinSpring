package lt.techin.movie_studio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonValidator.class)
public @interface Person {
  String message() default  "Person cannot be null, and must be capitalized";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
