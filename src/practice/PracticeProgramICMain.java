package practice;

import java.util.ArrayList;
import java.util.Arrays;

class PracticeProgram {

    // this variable should be static
     static int staticCounter = 0;

    // instance variable of type int
     int instanceCounter = 0;

    // code a Constructor that increments both the above variables
    public PracticeProgram(){
        this.instanceCounter++;
        PracticeProgram.staticCounter++;
    }

    // ---------------- STATIC METHODS ----------------

    // this method returns the sum of its two parameters
    public static int add(int a, int b) {
        return (a + b);
    }

    // complete the method signature of this Static method average
    // with varargs (variable parameters) of type double and returns a double
    public static double average(double... numbers)
    {
        if (numbers.length == 0) return 0;

        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.length;
    }

    // Static method returning an array
    //complete this method that generates the squares 
    //of integers 0 to n
    public static int[] generateSquares(int n) {
        int[] squares = new int[n+1];
        for(int i = 0; i <= n; i++) {
            squares[i] = i * i;
        }
        return squares;
    }

    // Static method returning an ArrayList
    public static ArrayList<String> convertToUpper(String[] words) {
        // create a local ArrayList
        ArrayList<String> result = new ArrayList<>(3);
        for (String word : words) {
            result.add(word.toUpperCase());
        }
        //return the array list
       return result;
    }

    // ---------------- NON-STATIC METHODS ----------------

    // complete the method signature
    public void displayMessage(String message) {
        System.out.println("Message: " + message);
    }

    // Instance method returning object
    //complete signature and method body
    public String concatenate(String a, String b) {
        //concatenate the two string objects passed to this method and return
        return (a + b);
    }

    // Instance method with array parameter
    //complete the method body to return the maximum number in numbers 
    public int findMax(int[] numbers) {
       int max = numbers[0];
        for(int num : numbers){
           if (num >= max){
               max = num;
           }
       }
       return max;
    }

    // Instance method with ArrayList parameter
    //complete the parameter list
    public int sumList(ArrayList<Integer> list) {
        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return sum;
    }

    // Method overloading
    public int multiply(int a, int b) {
        return a * b;
    }
    //code the overloaded method that takes two double parameters and returns their product
   public double multiply(double a, double b){
        return a * b;
   }

    // Method returning custom object (self)
    public PracticeProgram createNewInstance() {
        return new PracticeProgram();
    }
}
    // ---------------- MAIN METHOD ----------------
    public class PracticeProgramICMain{
    public static void main(String[] args) {

        // Using static methods
        //call the static method add with 5 and 10 as parameters
        System.out.println("Sum: " + PracticeProgram.add(5, 10));

        //call the static method average with parameters 10, 20, 30, 40

        System.out.println("Average: " + PracticeProgram.average(10, 20, 30, 40));

        //generate the squares of numbers 0 to 5 and print them
        int[] squares = PracticeProgram.generateSquares(5);
        System.out.println("Squares: " + Arrays.toString(squares));

        String[] words = {"java", "advanced", "program"};
        //complete the call
        ArrayList<String> upperWords = PracticeProgram.convertToUpper(words);
        System.out.println("Uppercase List: " + upperWords);

        // Creating object for non-static methods
        PracticeProgram demo = new PracticeProgram();

        //call displayMessage with 'Welcome to the course'
        demo.displayMessage("Welcome to the course");
        //concatenate Hello and World and print Hello World 
        System.out.println(demo.concatenate("concatenation: " + "Hello ", "World"));
        
        int[] nums = {3, 7, 2, 9, 5};
       //print the maximum of the numbers in nums
        System.out.println("Max: " + demo.findMax(nums));
        ArrayList<Integer> list = new ArrayList<>();
        //add the numbers 10, 20 and 30 to this array list
       list.add(10);
       list.add(20);
       list.add(30);
        //print the sum of these numbers now
        System.out.println("sum of list " + demo.sumList(list));

        // Method overloading demo
        //multiply 3 and 4 and print the result
        //multiply 2.5 and 3.5 and print the result
        System.out.println("Multiply int: " + demo.multiply(3,4));
        System.out.println("Multiply double: " + demo.multiply(2.5, 3.5));

        // create an object of type practice.PracticeProgram
         PracticeProgram newObj = new PracticeProgram();
        System.out.println("New Object Instance Counter: " + newObj.instanceCounter);
        //print the value of staticCounter
        System.out.println(("New Object Static Counter: " + newObj.staticCounter));
    }
}
