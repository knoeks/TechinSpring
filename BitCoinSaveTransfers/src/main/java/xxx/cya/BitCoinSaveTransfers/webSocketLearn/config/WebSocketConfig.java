package xxx.cya.BitCoinSaveTransfers.webSocketLearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // Enables WebSocket message handling backed by a message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // This is the endpoint that the client will use to connect to the WebSocket server.
    registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // Allow cross-origin (adjust for production)
            .withSockJS(); // Fallback options for browsers that don’t support WebSocket
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // Enable a simple in-memory broker with the "/topic" prefix for outgoing messages.
    registry.enableSimpleBroker("/topic");
    // Set the prefix for messages that are bound for methods annotated with @MessageMapping.
    registry.setApplicationDestinationPrefixes("/app");
  }
}
