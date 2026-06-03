/*
This code demonstrates a simple event-driven messaging system using the Observer (Publisher–Subscriber) pattern in Java.
The Observer Pattern is the foundation of event handling systems, including:
GUI events (buttons, clicks)
File watchers
Messaging systems
Real-time notifications(emial, stock market apps)

A publisher to send messages
Multiple subscribers (listeners) to receive those messages
Each subscriber reacts when an “event” (message) is sent
Examples: YouTube subscriptions, Weather app alerts, Stock market app, Fire alarm system, Fire alarm system, School Notification System, GUI buttons in Java
 */
package wk4;
import java.util.ArrayList;
import java.util.List;

// Listener interface (event handler)
interface MessageListener {

    void onMessage(String message);
}

// Publisher (event source)
class MessagePublisher {

    private List<MessageListener> listeners = new ArrayList<>();

    void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    void sendMessage(String msg) {
        for (MessageListener listener : listeners) {
            try {
                listener.onMessage(msg); // event fired
            } catch (Exception e) {
                System.out.println("Error delivering message to a listener: " + e.getMessage());
            }
        }
    }
}

// Subscriber (event handler)
//subscribers can be of different types- as long as the subscriber implements the MessageListener and defines the onMessage method to react differently to the same event
class MessageSubscriber implements MessageListener {

    public void onMessage(String message) {
        try {
            System.out.println("Subscriber instance: " + this);
            // Simulate possible error
            if (message == null) {
                throw new IllegalArgumentException("Message cannot be null");
            }

            System.out.println("Received message: " + message);

        } catch (Exception e) {
            System.out.println("Subscriber error: " + e.getMessage());
        }
    }
}

public class MessagingExample {

    public static void main(String[] args) {

        try {
            MessagePublisher publisher = new MessagePublisher();

            MessageSubscriber s1 = new MessageSubscriber();
            MessageSubscriber s2 = new MessageSubscriber();
            MessageSubscriber s3 = new MessageSubscriber();

            publisher.addListener(s1);
            publisher.addListener(s2);
            publisher.addListener(s3);

            publisher.sendMessage("Hello everyone!");

            publisher.sendMessage(null); // test exception

        } catch (Exception e) {
            System.out.println("Unexpected error in main: " + e.getMessage());
        }
    }
}
