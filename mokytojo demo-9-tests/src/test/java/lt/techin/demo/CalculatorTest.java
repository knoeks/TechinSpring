package lt.techin.demo;

import lt.techin.demo.model.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

  @Test
  void testAddition() {
    Calculator calculator = new Calculator();

    assertEquals(5, calculator.add(2, 3)); // true; testas žaliuoja
  }
}
