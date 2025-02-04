package xxx.cya.BitCoinSaveTransfers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xxx.cya.BitCoinSaveTransfers.websocketOld.BlockchainWebSocketClient;

@SpringBootApplication
public class BitCoinSaveTransfersApplication {

  public static void main(String[] args) {
//    try {
//      new BlockchainWebSocketClient();
//    } catch (Exception e) {
//      System.out.println("===================================");
//      System.out.println(e);
//      System.out.println("===================================");
//    }
    SpringApplication.run(BitCoinSaveTransfersApplication.class, args);
  }

}
