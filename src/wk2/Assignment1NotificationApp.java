/*
You are building a Notification System that can send messages through different channels:

Email
SMS
Push Notification
 */
package wk2;

// ===== INTERFACE =====
interface NotificationMethod {

    void send(String message);

    String getType();
}

// ===== ABSTRACT CLASS =====
abstract class AbstractNotification implements NotificationMethod {

    protected String recipient;

    public AbstractNotification(String recipient) {
        this.recipient = recipient;
    }

    // TODO 1:
    // Create a concrete method called logNotification()
    // It should print: "Sending notification to <recipient>"
    public void logNotification(String recipient){
        System.out.println("Sending notification to " + recipient);
    }
    // TODO 2:
    // Create an abstract method called validate() with void return type
    public abstract void validate();
}

// ===== CONCRETE CLASS 1 =====
class EmailNotification extends AbstractNotification {

    private String emailAddress;

    public EmailNotification(String recipient, String emailAddress) {
        super(recipient);
        this.emailAddress = emailAddress;
    }

    // TODO 3:
    // Implement validate()
    // Print: "Validating email: <emailAddress>"
   @Override
    public void validate(){
        System.out.println("Validating email: " + emailAddress);
    }
    // TODO 4:
    // Implement send()
    // Steps:
    // 1. Call validate()
    // 2. Print "Sending Email..."
    // 3. Call logNotification()

    @Override
    public void send(String message) {
        validate();
        System.out.println("Sending Email...");
        logNotification(recipient);
    }

    // TODO 5:
    // Implement getType() → return "Email"

    @Override
    public String getType() {
        return "Email";
    }
}

// ===== CONCRETE CLASS 2 =====
class SMSNotification extends AbstractNotification {

    private String phoneNumber;

    public SMSNotification(String recipient, String phoneNumber) {
        super(recipient);
        this.phoneNumber = phoneNumber;
    }

    // TODO 6:
    // Implement validate()
    // Print: "Validating phone number: <phoneNumber>"

    @Override
    public void validate() {
        System.out.println("Validating phone number: " + phoneNumber);
    }

    // TODO 7:
    // Implement send()

    @Override
    public void send(String message) {
        validate();
        System.out.println("Sending SMS...");
        logNotification(recipient);
    }

    // TODO 8:
    // Implement getType() → return "SMS"

    @Override
    public String getType() {
        return "SMS";
    }
}

// ===== CONCRETE CLASS 3 =====
class PushNotification extends AbstractNotification {

    private String deviceId;

    public PushNotification(String recipient, String deviceId) {
        super(recipient);
        this.deviceId = deviceId;
    }

    // TODO 9:
    // Implement validate()

    @Override
    public void validate() {
        System.out.println("Validating Device Id: " + deviceId);
    }

    // TODO 10:
    // Implement send()

    @Override
    public void send(String message) {
        validate();
        System.out.println("Sending Push Notification...");
        logNotification(recipient);
    }

    // TODO 11:
    // Implement getType() → return "Push"

    @Override
    public String getType() {
        return "Push";
    }
}

// ===== UTILITY CLASS (OVERLOADING) =====
class NotificationUtils {

    // TODO 12:
    // Overload method notifyUser(String message)
    // Print: "Standard notification: <message>"
    public void notifyUser(String message){
        System.out.println("Standard notification: " + message);
    }
    // TODO 13:
    // Overload method notifyUser(String message, String priority)
    // Print: "Priority <priority> notification: <message>"
    public void notifyUser(String message, String priority){
        System.out.println("Priority " + priority + "notification: " + message);
    }
}

// ===== MAIN DRIVER =====
public class Assignment1NotificationApp {

    public static void main(String[] args) {

        // TODO 14:
        // Create objects:
        // EmailNotification, SMSNotification, PushNotification
        NotificationMethod n1 =  new EmailNotification("Alice", "alice@email.com");
        NotificationMethod n2 = new SMSNotification("Bob", "123-456-7890");
        NotificationMethod n3 = new PushNotification("Charlie", "device123");
        // TODO 15:
        // Store them in an array of NotificationMethod
        NotificationMethod[] notifications = {n1, n2, n3};
        // TODO 16:
        // Loop through and:
        // 1. Print notification type
        // 2. Call send("Hello User!")
        for (NotificationMethod notification : notifications){
            System.out.println("\nUsing: " + notification.getType());
            notification.send("Hello User!");
        }
        // TODO 17:
        // Create NotificationUtils object
        // Call both overloaded methods
        NotificationUtils utils = new NotificationUtils();
        utils.notifyUser("Welcome!");
        utils.notifyUser("System Alert!", "HIGH ");
    }
}
