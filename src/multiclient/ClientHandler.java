package multiclient;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("Client connected: " + socket);

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true)) {

            String message;

            while ((message = in.readLine()) != null) {

                System.out.println("Received: " + message);

                out.println(message); // Echo back
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Client disconnected: " + socket);
    }
}
