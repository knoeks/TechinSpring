package xxx.cya.BitCoinSaveTransfers.webSocketLearn.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import xxx.cya.BitCoinSaveTransfers.webSocketLearn.model.Message;

@Controller
public class ChatController {

  // When a client sends a message to /app/chat, this method is invoked.
  @MessageMapping("/chat")
  // The return value is sent to subscribers of /topic/messages.
  @SendTo("/topic/messages")
  public Message send(Message message) throws Exception {
    // You can add processing logic here if needed.
    return message;
  }
}
