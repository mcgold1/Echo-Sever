/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael Goldman
 */
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TCPEchoClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    String server = args[0];       // Server name or IP address
    // Convert argument String to bytes using the default character encoding
    byte[] data = args[1].getBytes();

    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();

        
    out.write(data, 0, data.length-3);  // Send the encoded string to the server
    socket.close(); //Attacks the server by closing the socket before it recives the data back
    
    Socket socket2 = new Socket(server, servPort); //Contiunes the attack by opening up a new new socket and sending the rest of the data
    out = socket2.getOutputStream();
    in = socket2.getInputStream();
    out.write(data,data.length - 3, 3); //sends the rest of the data in an attack to attempt to break the output stream

    // Receive the same string back from the server
    int totalBytesRcvd = 0;  // Total bytes received so far
    int bytesRcvd;           // Bytes received in last read
    while (totalBytesRcvd < 3) {
      if ((bytesRcvd = in.read(data, totalBytesRcvd,  
                        data.length - totalBytesRcvd)) == -1)
        throw new SocketException("Connection closed prematurely");
      System.out.println("Input recieved");
      totalBytesRcvd += bytesRcvd;
    }  // data array is full

    System.out.println("Received: " + new String(data));

    socket.close();  // Close the socket and its streams
  }
}