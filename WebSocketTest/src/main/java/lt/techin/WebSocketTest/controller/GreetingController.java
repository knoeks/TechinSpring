package lt.techin.WebSocketTest.controller;

import lt.techin.WebSocketTest.model.Greeting;
import lt.techin.WebSocketTest.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


  // sitas is kliento pasiima duomenis
  @MessageMapping("/hello")
  // sitas subsribina duomenu perdavimo sistemai ir klausosi naujiniu
  @SendTo("/topic/greetings")
  // payload is bound to HelloMessage object
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000);

    // sitas htmlEscape escapina specialius simbolius kad negaletu scripto parasyt ir kazkaip data access gaut
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
}
