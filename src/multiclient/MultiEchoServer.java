package multiclient;

import java.io.*;
import java.net.*;

public class MultiEchoServer {

    public static void main(String[] args) {

        final int PORT = 5000;

        System.out.println("Multithreaded Echo Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("Accepted connection: " + socket);

                // Create a new thread for each client
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
