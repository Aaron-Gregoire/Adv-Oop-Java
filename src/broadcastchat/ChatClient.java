package broadcastchat;

import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PORT = 5000;

        try {
            Socket socket = new Socket(HOST, PORT);

            BufferedReader keyboard
                    = new BufferedReader(new InputStreamReader(System.in));

            BufferedReader in
                    = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out
                    = new PrintWriter(socket.getOutputStream(), true);

            // Get username
            System.out.print("Enter username: ");
            String username = keyboard.readLine();

            System.out.println("""
                    -----------------------------------
                    Connected to Chat Server
                    Type 'quit' to exit
                    -----------------------------------
                    """);

            // Create the Receiver thread/define its run method - reads messages from the 'echo' server
            Thread receiver = new Thread(() -> {
                try {
                    String message;

                    while ((message = in.readLine()) != null) {
                        System.out.println("\n" + message);
                        System.out.print(username + " > ");
                    }

                } catch (IOException e) {
                    System.out.println("\nDisconnected from server.");
                }
            });
            //this is a background thread.When main() finishes, JVM exits
            receiver.setDaemon(true);
            //begin executing its run() method
            receiver.start();

            // Sender loop - 'main' thread is sending messages
            while (true) {

                System.out.print(username + " > ");
                String message = keyboard.readLine();

                if (message == null || message.equalsIgnoreCase("quit")) {
                    out.println(username + " left the chat");
                    break;
                }

                out.println(username + ": " + message);
            }

            socket.close();
            System.out.println("You exited the chat.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
