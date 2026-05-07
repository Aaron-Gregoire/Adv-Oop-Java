package wk1;

import java.util.Arrays;

class BankAccount {

    //fields- what does the private access specifier mean and what does it ensure?
    //they 'define' the 'look' of the object, its data. Data is usually private and is not accessible outside the object
    //what are getter and setter methods?
    private String owner;
    private double balance;

    // Constructor, the 'method' to create objects of this class
    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    //methods determine what an object can 'do'. They are usually public but private methods(helpers) can be present too
    // 1. Void method (no return)
    public void deposit(double amount) {
        balance += amount;
        System.out.println(owner + " deposited: $" + amount);
    }

    // 2. Method with return value
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // 3. Method returning a primitive
    public double getBalance() {
        return balance;
    }

    // 4. Method with multiple parameters
    public void transfer(BankAccount target, double amount) {
        if (this.withdraw(amount)) {
            target.deposit(amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed: insufficient funds.");
        }
    }

    // 5. Method returning a String
    public String getAccountSummary() {
        return "Owner: " + owner + ", Balance: $" + balance;
    }

    // 6. Method with array parameter
    public double calculateAverageTransaction(double[] transactions) {
        double sum = 0;
        for (double t : transactions) {
            sum += t;
        }
        return transactions.length > 0 ? sum / transactions.length : 0;
    }

    // 7. Method returning an array
    public double[] applyInterestRates(double[] rates) {
        double[] results = new double[rates.length];
        for (int i = 0; i < rates.length; i++) {
            results[i] = balance * (1 + rates[i]);
        }
        return results;
    }

    // 8. Static method
    public static String bankPolicy() {
        return "Minimum balance must be non-negative.";
    }

    // 9. Method overloading
    public void deposit(int amount) {
        deposit((double) amount);
    }

    // 10. Varargs method (accepts any number of arguments)
    public double sumTransactions(double... amounts) {
        double total = 0;
        for (double amt : amounts) {
            total += amt;
        }
        return total;
    }

    //11. Varargs with regular parameters method 
    public void printTaggedTransactions(String tag, double... amounts) {
        System.out.print(tag + ": ");
        for (double amt : amounts) {
            System.out.print(amt + " ");
        }
        System.out.println();
    }
}
//there can be only one public class in a Java file and it should contain the main metod, which controls what needs to be done

public class BankAccountApp {

    public static void main(String[] args) {

        //create objects of the wk1.BankAccount class
        BankAccount acc1 = new BankAccount("Alice", 1000);
        BankAccount acc2 = new BankAccount("Bob", 500);

        // calling different methods
        acc1.deposit(200);
        acc1.deposit(150.75);

        boolean success = acc1.withdraw(300);
        System.out.println("Withdraw success: " + success);

        acc1.transfer(acc2, 250);

        System.out.println(acc1.getAccountSummary());
        System.out.println(acc2.getAccountSummary());

        double total = acc1.sumTransactions(100, -50, 200, -25.5);  // multiple values
        System.out.println("Total of transactions: " + total);

        double[] transactions = {100, -50, 200, -25.5};
        double avg = acc1.calculateAverageTransaction(transactions);
        System.out.println("Average transaction: " + avg);

        total = acc1.sumTransactions();            // no values, returns 0
        System.out.println("Total of transactions: " + total);
        total = acc1.sumTransactions(new double[]{5, 5}); //array input
        System.out.println("Total of transactions: " + total);

        acc1.printTaggedTransactions("Expenses", 10, 20, 30);

        double[] rates = {0.01, 0.05, 0.1};
        double[] projectedBalances = acc1.applyInterestRates(rates);
        System.out.println("Projected balances: " + Arrays.toString(projectedBalances));

        acc1.sumTransactions(10, 20, 30);        // multiple values
        acc1.sumTransactions();                  // no values (returns 0)
        acc1.sumTransactions(new double[]{5, 5}); // array input

        System.out.println(BankAccount.bankPolicy());
    }
}
