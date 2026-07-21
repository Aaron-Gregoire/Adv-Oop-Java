package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * EchoServer.java
 *
 * Lesson 1 - TCP Networking
 *
 * This program demonstrates a simple TCP Echo Server.
 *
 * An echo server simply sends back (echoes) whatever
 * message the client sends.
 *
 * Concepts demonstrated:
 * ---------------------
 * • ServerSocket
 * • Socket
 * • TCP communication
 * • BufferedReader
 * • PrintWriter
 * • Infinite server loop
 * • Blocking methods
 *
 * Communication Flow
 * ------------------
 *
 * Client -----> Server
 *        Hello
 *
 * Client <----- Server
 *        Hello
 *
 */
public class EchoServer {

    // Port number on which the server will listen
    private static final int PORT = 5000;

    public static void main(String[] args) {

        System.out.println("Starting Echo Server...");

        try (
                // Create the server socket
                ServerSocket serverSocket = new ServerSocket(PORT);) {

            System.out.println("Server is listening on port " + PORT);
            System.out.println("Waiting for a client...\n");

            //waits (blocks) until a client connects and then returns a Socket object representing that connection.
            Socket socket = serverSocket.accept();

            System.out.println("Client connected!");
            System.out.println();

            // Input stream (receive data)
            BufferedReader in
                    = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            // Output stream (send data)
            PrintWriter out
                    = new PrintWriter(
                            socket.getOutputStream(),
                            true);

            String message;

            // Continue until client disconnects
            while ((message = in.readLine()) != null) {

                System.out.println("Client: " + message);

                // Echo message back
                out.println("Server Echo: " + message);

                // Allow client to quit
                if (message.equalsIgnoreCase("quit")) {

                    System.out.println("Client disconnected.");
                    break;
                }
            }

            socket.close();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        System.out.println("Server terminated.");

    }

}
