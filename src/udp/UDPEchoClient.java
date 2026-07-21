package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPEchoClient {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PORT = 5000;

        try (
                DatagramSocket socket = new DatagramSocket(); BufferedReader keyboard
                = new BufferedReader(
                        new InputStreamReader(System.in))) {

            System.out.println("""
                    ==============================
                    UDP Echo Client
                    Type a message and press Enter.
                    Type 'quit' to exit.
                    ==============================
                    """);

            while (true) {

                System.out.print("Message: ");
                String message = keyboard.readLine();

                if (message == null
                        || message.equalsIgnoreCase("quit")) {
                    break;
                }

                // Convert the message into bytes.
                byte[] sendData = message.getBytes();

                // Create a packet addressed to the server.
                DatagramPacket packet
                        = new DatagramPacket(
                                sendData,
                                sendData.length,
                                InetAddress.getByName(HOST),
                                PORT);

                // Send the packet.
                socket.send(packet);

                // Prepare a buffer for the reply.
                byte[] receiveData = new byte[1024];

                DatagramPacket reply
                        = new DatagramPacket(
                                receiveData,
                                receiveData.length);

                // Wait for the echoed packet.
                socket.receive(reply);

                String echoed
                        = new String(
                                reply.getData(),
                                0,
                                reply.getLength());

                System.out.println("Server replied: " + echoed);
            }

            System.out.println("Client closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
