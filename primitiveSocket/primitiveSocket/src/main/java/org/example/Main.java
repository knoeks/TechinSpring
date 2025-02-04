package org.example;

import java.io.IOException;
import java.net.Socket;

public class Main {
  public static void main(String[] args) throws IOException {

    Socket socket = null;
    try {
      socket = new Socket("time.nist.gov", 13);
      // read from socket
    } catch (IOException e) {
      System.err.println("could not connect to time.nist.gov");
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {

        }


      }
    }
  }
}