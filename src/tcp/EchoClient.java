package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * EchoClient.java
 *
 * Lesson 1 - TCP Networking
 *
 * Connects to the Echo Server.
 *
 * The user types messages.
 * The server sends the same message back.
 *
 * Concepts demonstrated
 * ---------------------
 * • Socket
 * • TCP connection
 * • Keyboard input
 * • Sending text
 * • Receiving text
 *
 */
public class EchoClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        System.out.println("Starting Echo Client...\n");

        try (
                // Connect to server
                Socket socket = new Socket(HOST, PORT); // Read keyboard
                 Scanner keyboard = new Scanner(System.in); // Read from server
                 BufferedReader in
                = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream())); // Send to server
                 PrintWriter out
                = new PrintWriter(
                        socket.getOutputStream(),
                        true);) {

            System.out.println("Connected to server.");
            System.out.println("Type messages.");
            System.out.println("Type 'quit' to exit.\n");

            while (true) {

                System.out.print("You: ");

                String message = keyboard.nextLine();

                // Send message
                out.println(message);

                // Receive echo
                String reply = in.readLine();

                System.out.println(reply);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        System.out.println("\nClient terminated.");

    }

}
