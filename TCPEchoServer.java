/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael Goldman
 */
import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

    while (true) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection

      SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
      System.out.println("Handling client at " + clientAddress);
      
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      // Receive until client closes connection, indicated by -1 return
       try {
            recvMsgSize = in.read(receiveBuf);
              }
         catch (Exception e) {
                recvMsgSize = -1;
        }
       
      while (recvMsgSize  != -1) {
        try { // projects the server from a socket closing premuterly as it trys to write to it, quits out of the loop and closes the contenction if failed
        out.write(receiveBuf, 0, recvMsgSize);
        }
         catch (Exception e) {
                recvMsgSize = -1;
        }
        
        try { //Once again project the server from simular attacks except in regards to receiving data
            recvMsgSize = in.read(receiveBuf);
              }
         catch (Exception e) {
                recvMsgSize = -1;
        }
      }
      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
