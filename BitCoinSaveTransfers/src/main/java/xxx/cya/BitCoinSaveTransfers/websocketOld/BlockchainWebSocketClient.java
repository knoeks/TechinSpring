package xxx.cya.BitCoinSaveTransfers.websocketOld;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ExecutionException;

public class BlockchainWebSocketClient {

  private static final String WEBSOCKET_URL = "wss://ws.blockchain.info/prices";

  public BlockchainWebSocketClient() throws ExecutionException, InterruptedException {
    secondMethod();
  }

  public static void secondMethod() throws ExecutionException, InterruptedException {
    StandardWebSocketClient client = new StandardWebSocketClient();

    WebSocketSession session = client.execute(new TextWebSocketHandler() {
      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to WebSocket!");

        // Subscribe to BTC-USD ticker updates
        String subscribeMessage = "{ \"op\": \"subscribe\", \"channel\": \"ticker\", \"symbol\": \"BTC-USD\" }";
        session.sendMessage(new TextMessage(subscribeMessage));
      }

      @Override
      protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received: " + message.getPayload());
      }
    }, WEBSOCKET_URL).get(); // Use URI.create() instead of deprecated string-based method
  }

//  public static ListenableFuture<WebSocketSession> firstMethod() {
//    StandardWebSocketClient client = new StandardWebSocketClient();
//
//    return client.doHandshake(new TextWebSocketHandler() {
//      @Override
//      public void handleTextMessage(org.springframework.web.socket.WebSocketSession session, TextMessage message) {
//        System.out.println("Received: " + message.getPayload());
//      }
//
//      @Override
//      public void afterConnectionEstablished(org.springframework.web.socket.WebSocketSession session) throws Exception {
//        System.out.println("Connected to WebSocket!");
//
//        // Subscribe to BTC-USD ticker updates
//        String subscribeMessage = "{ \"op\": \"subscribe\", \"channel\": \"ticker\", \"symbol\": \"BTC-USD\" }";
//        session.sendMessage(new TextMessage(subscribeMessage));
//      }
//    }, WEBSOCKET_URL);
//  }
}
