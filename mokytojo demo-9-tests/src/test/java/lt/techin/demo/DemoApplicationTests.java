package lt.techin.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Testuoja viską - ir controller, ir service, ir visą kitą; tai labiau end-to-end
@SpringBootTest
class DemoApplicationTests {

  // 305 ms?! Nes tikrina visą Spring
  @Test
  void contextLoads() {
  }

}
