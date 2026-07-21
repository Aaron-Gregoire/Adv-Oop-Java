package broadcastchat;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final CopyOnWriteArrayList<ClientHandler> clients;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket,
            CopyOnWriteArrayList<ClientHandler> clients) {

        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {

        try {

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            String message;

            while ((message = in.readLine()) != null) {

                System.out.println("Received: " + message);

                // Leaving the chat?
                if (message.endsWith("left the chat")) {
                    broadcastToAll(message);
                    break;
                }

                // Normal chat message
                broadcastToOthers(message);
            }

        } catch (IOException e) {

            // Ignore expected disconnects.
            System.out.println("Client disconnected.");

        } finally {

            closeConnection();
        }
    }

    // Send to everyone EXCEPT the sender
    private void broadcastToOthers(String message) {

        for (ClientHandler client : clients) {

            if (client != this) {
                client.out.println(message);
            }
        }
    }

    // Send to everyone INCLUDING the sender
    private void broadcastToAll(String message) {

        for (ClientHandler client : clients) {
            client.out.println(message);
        }
    }

    private void closeConnection() {

        clients.remove(this);

        try {

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
