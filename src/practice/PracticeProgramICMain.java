package practice;

import java.util.ArrayList;

class PracticeProgram {

    // this variable should be static
     int staticCounter = 0;

    // instance variable of type int
    instanceCounter = 0;

    // code a Constructor that increments both the above variables
 

    // ---------------- STATIC METHODS ----------------

    // this method returns the sum of its two parameters
    public static int add(int a, int b) {
        
    }

    // complete the method signature of this Static method average
    // with varargs (variable parameters) of type double and returns a double
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
        
    }

    // Static method returning an ArrayList
    public static ArrayList<String> convertToUpper(String[] words) {
        // create a local ArrayList
      
        for (String word : words) {
            result.add(word.toUpperCase());
        }
        //return the array list
       
    }

    // ---------------- NON-STATIC METHODS ----------------

    // complete the method signature
    public  displayMessage(String message) {
        System.out.println("Message: " + message);
    }

    // Instance method returning object
    //complete signature and method body
    public concatenate(String a, String b) {
        //concatenate the two string objects passed to this method and return
    }

    // Instance method with array parameter
    //complete the method body to return the maximum number in numbers 
    public int findMax(int[] numbers) {
       
    }

    // Instance method with ArrayList parameter
    //complete the parameter list
    public int sumList() {
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
        System.out.println("Sum: " + );

        //call the static method average with parameters 10, 20, 30, 40

        System.out.println("Average: " + );

        //generate the squares of numbers 0 to 5 and print them
        

        String[] words = {"java", "advanced", "program"};
        //complete the call
        = PracticeProgram.convertToUpper(words);
        System.out.println("Uppercase List: " + upperWords);

        // Creating object for non-static methods
        PracticeProgram demo = new PracticeProgram();

        //call displayMessage with 'Welcome to the course'
        
        //concatenate Hello and World and print Hello World 

        
        int[] nums = {3, 7, 2, 9, 5};
       //print the maximum of the numbers in nums

        ArrayList<Integer> list = new ArrayList<>();
        //add the numbers 10, 20 and 30 to this array list
       
        //print the sum of these numbers now
      

        // Method overloading demo
        //multiply 3 and 4 and print the result
        //multiply 2.5 and 3.5 and print the result
        

        // create an object of type practice.PracticeProgram
       
        System.out.println("New Object Instance Counter: " + newObj.instanceCounter);
        //print the value of staticCounter

    }
}
