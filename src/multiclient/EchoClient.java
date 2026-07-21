package multiclient;

import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PORT = 5000;

        try (
                Socket socket = new Socket(HOST, PORT); BufferedReader keyboard
                = new BufferedReader(new InputStreamReader(System.in)); BufferedReader in
                = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())); PrintWriter out
                = new PrintWriter(socket.getOutputStream(), true);) {

            System.out.println("""
                    Connected to Multithreaded Echo Server.

                    Type messages and press Enter.
                    The server will echo them back.
                    Type 'quit' to disconnect.
                    """);

            String message;

            while (true) {

                System.out.print("Enter message: ");
                message = keyboard.readLine();

                if (message == null || message.equalsIgnoreCase("quit")) {
                    break;
                }

                // send to server
                out.println(message);

                // receive echo
                String reply = in.readLine();

                System.out.println("Server replied: " + reply);
            }

            System.out.println("Disconnected from server.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
