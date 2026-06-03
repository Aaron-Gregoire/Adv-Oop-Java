/*
This code is a simple TCP client. Its purpose is to connect to a server running on your computer (port 5000) and send messages to it.
 */
package wk4;
import java.io.*;
import java.net.*;

public class ClientExample {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to server");

            // Send messages to server
            out.println("Hello Server!");
            out.println("This is client speaking");
            //closes connection automatically when try with resources ends

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
