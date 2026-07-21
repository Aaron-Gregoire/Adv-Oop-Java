package broadcastchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    public static void main(String[] args) {

        final int PORT = 5000;
        /* Shared list of all connected clients
        CopyOnWriteArrayList is
        1. Thread-safe by default; 2. No ConcurrentModificationException; 3. Safe iteration without locking
        4. Predictable behavior in concurrent environments; 5. Ideal for read-heavy systems; 6. No need for manual synchronization
        Every modification (add/remove) creates a new copy of the array, this makes writes more expensive than ArrayList; not suitable for write-heavy systems
        CopyOnWriteArrayList works by giving each reader a snapshot of the list, while any modification creates a new copy instead of changing the existing on
         */

        CopyOnWriteArrayList<ClientHandler> clients
                = new CopyOnWriteArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Chat Server started...");
            System.out.println("Waiting for clients...");

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("Client connected: "
                        + socket.getInetAddress());

                ClientHandler client
                        = new ClientHandler(socket, clients);

                clients.add(client);

                new Thread(client).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
