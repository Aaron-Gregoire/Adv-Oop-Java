/*
Interface = “what to do”
Abstract class = “shared behavior”
Polymorphism = “same type, different behavior”
Overloading = “same method, different parameters”
 */
 /*Use an interface when:
    You want to define a contract for unrelated classes
    You need multiple inheritance
Use an abstract class when:
    You want to share code among closely related classes
    You need constructors or instance variables
 */
package wk2;
 /*
Interface - 100% abstraction traditionally, meaning they define what to do, not how to do it. A contract per se
Cannot be instantiated
All variables are implicitly 'public static final'; no instance variables
Cannnot have constructor(s)
Methods are public and abstract by default
Multiple inheritance is supported
Can extend other interfaces

Java 8+ allows default and static methods with implementations
Java 9+, private methods are allowed
 */
interface PaymentMethod {

    void pay(double amount);

    String getPaymentType();
}

/*
Abstract Class
Provides a base class with shared code and behavior.
Can have abstract methods (no body) but also have fully implemented methods
Can have instance variables, static variables, and constants
Can have constructors (used when subclass is created)
A class can extend only one abstract class
Methods and variables can have any access modifier (private, protected, etc.)
 */
abstract class AbstractPayment implements PaymentMethod {

    protected String accountHolder;

    public AbstractPayment(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // Concrete method
    public void printReceipt(double amount) {
        String result = String.format("%.2f", amount);
        System.out.println("Receipt: " + accountHolder + " paid $" + result);
    }

    // Abstract method
    public abstract void validate();
}

// Concrete Class 1
class CreditCardPayment extends AbstractPayment {

    private String cardNumber;

    public CreditCardPayment(String accountHolder, String cardNumber) {
        super(accountHolder);
        this.cardNumber = cardNumber;
    }

    @Override
    public void validate() {
        System.out.println("Validating credit card: " + cardNumber);
    }

    @Override
    public void pay(double amount) {
        validate();
        System.out.println("Processing credit card payment...");
        printReceipt(amount);
    }

    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
}

// Concrete Class 2
class PayPalPayment extends AbstractPayment {

    private String email;

    public PayPalPayment(String accountHolder, String email) {
        super(accountHolder);
        this.email = email;
    }

    @Override
    public void validate() {
        System.out.println("Validating PayPal account: " + email);
    }

    @Override
    public void pay(double amount) {
        validate();
        System.out.println("Processing PayPal payment...");
        printReceipt(amount);
    }

    @Override
    public String getPaymentType() {
        return "PayPal";
    }
}

// Another Concrete Class (demonstrates polymorphism further)
class CryptoPayment extends AbstractPayment {

    private String walletAddress;

    public CryptoPayment(String accountHolder, String walletAddress) {
        super(accountHolder);
        this.walletAddress = walletAddress;
    }

    @Override
    public void validate() {
        System.out.println("Validating crypto wallet: " + walletAddress);
    }

    @Override
    public void pay(double amount) {
        validate();
        System.out.println("Processing crypto payment...");
        printReceipt(amount);
    }

    @Override
    public String getPaymentType() {
        return "Cryptocurrency";
    }
}

// Main Class (Driver)
public class PaymentProcessingSystem {

    public static void main(String[] args) {

        // Polymorphism (runtime)
        PaymentMethod p1 = new CreditCardPayment("Alice", "1234-5678");
        PaymentMethod p2 = new PayPalPayment("Bob", "bob@email.com");
        PaymentMethod p3 = new CryptoPayment("Charlie", "0xABC123");

        PaymentMethod[] payments = {p1, p2, p3};

        for (PaymentMethod payment : payments) {
            System.out.println("\nUsing: " + payment.getPaymentType());
            payment.pay(100.00); // Runtime polymorphism
        }

        // Compile-time polymorphism (method overloading)
        PaymentUtils utils = new PaymentUtils();
        utils.process(50);
        utils.process(75, "Priority");
    }
}

// Utility class for method overloading
class PaymentUtils {

    public void process(double amount) {
        String result = String.format("%.2f", amount);
        System.out.println("Processing standard payment: $" + result);
    }

    public void process(double amount, String priority) {
        String result = String.format("%.2f", amount);
        System.out.println("Processing " + priority + " payment: $" + result);
    }
}
