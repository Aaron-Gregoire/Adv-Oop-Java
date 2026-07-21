package udp;
/*
    TCP        vs	     UDP
Connection-oriented	Connectionless
Reliable	        Best effort
Ordered	            May arrive out of order
Streams	            Packets
Socket	            DatagramSocket
ServerSocket	    DatagramSocket
BufferedReader	    DatagramPacket

| Application               | TCP | UDP | Why?                                                 |
| ------------------------- | :-: | :-: | ---------------------------------------------------- |
| Web browsing (HTTP/HTTPS) |  ✔  |     | Every byte matters.                                  |
| Email                     |  ✔  |     | Messages must be complete.                           |
| File transfer             |  ✔  |     | Files cannot be corrupted.                           |
| Online banking            |  ✔  |     | Accuracy is essential.                               |
| Database connections      |  ✔  |     | Transactions must be reliable.                       |
| Video conferencing        |     |  ✔  | Low latency is more important than perfect delivery. |
| Online gaming             |     |  ✔  | Fast updates matter more than retransmission.        |
| Voice calls               |     |  ✔  | Minor packet loss is preferable to delays.           |
| Live streaming            |     |  ✔  | Smooth playback is more important than every frame.  |
| DNS lookups               |     |  ✔  | Small requests; easy to retry if necessary.          |
| IoT sensors               |     |  ✔  | Frequent updates make occasional loss acceptable.    |

 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {

    public static void main(String[] args) {

        final int PORT = 5000;

        // A DatagramSocket is used for both sending and receiving UDP packets.
        try (DatagramSocket socket = new DatagramSocket(PORT)) {

            System.out.println("==================================");
            System.out.println(" UDP Echo Server Started");
            System.out.println(" Listening on port " + PORT);
            System.out.println(" Press Ctrl+C to stop");
            System.out.println("==================================");

            while (true) {

                // Allocate a buffer to hold the incoming data.
                byte[] buffer = new byte[1024];

                // Create a packet that will receive the client's data.
                DatagramPacket packet
                        = new DatagramPacket(buffer, buffer.length);

                System.out.println("\nWaiting for a packet...");

                // Blocks until a packet arrives.
                socket.receive(packet);

                // Convert only the received bytes into a String.
                String message = new String(
                        packet.getData(),
                        0,
                        packet.getLength());

                System.out.println("Received: " + message);

                // Echo the same packet back to the sender.
                DatagramPacket reply
                        = new DatagramPacket(
                                packet.getData(),
                                packet.getLength(),
                                packet.getAddress(),
                                packet.getPort());

                socket.send(reply);

                System.out.println("Echo sent.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
