/*
This program is a basic TCP server. Its purpose is to listen for incoming connections and read messages sent by those connections.
Handles one connection at a time (no multithreading)
Only reads one line per connection
Does not send any response back
 */
package wk4;
import java.io.*;
import java.net.*;

public class ServerExample {

    public static void main(String[] args) {

        ServerSocket server = null;

        try {
            server = new ServerSocket(5000);
            System.out.println("Server listening...");

            while (true) {
                Socket connection = null;

                try {
                    // Event: connection connects
                    connection = server.accept();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    );

                    // Event: message received
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                    }

                    if (message == null) {
                        System.out.println("Connection closed by Client");
                    }

                } catch (IOException e) {
                    System.out.println("Error handling connection: " + e.getMessage());

                } finally {
                    // Always close connection socket
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (IOException e) {
                            System.out.println("Error closing connection: " + e.getMessage());
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());

        } finally {
            // Clean up server socket
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println("Error closing server: " + e.getMessage());
                }
            }
        }
    }
}

/*
Same code with try/resources
import java.io.*;
import java.net.*;

public class ServerExample {

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("Server listening...");

            while (true) {

                // Each connection connection with try-with-resources
                try (
                    Socket connection = server.accept();
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                    )
                ) {

                    // Event: message received
                     String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                    }

                    if (message == null) {
                        System.out.println("Connection closed by Client");
                    }

                } catch (IOException e) {
                    System.out.println("Error handling connection: " + e.getMessage());
                   
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            
    }
} */
