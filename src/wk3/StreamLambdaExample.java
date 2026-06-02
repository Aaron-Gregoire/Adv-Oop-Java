package wk3;/*
Java Lambdas
What is it?
A lambda in Java is a shorthand for creating an implementation of a functional interface, 
which the compiler internally converts into an anonymous class with a single method.

A lambda expression is a short way to write a small function in Java.
Introduced in Java 8.

Makes code shorter, cleaner, and easier to read.

Where is it used?
Anywhere a functional interface is required (interface with one abstract method).
As a method reference
Common use cases in JavaFX:
TableView selection listeners
TextField text change listeners
Button click handlers (ActionEvent)
FilteredList predicates
Also used in:
Runnable, Comparator, Stream operations, forEach loops, etc.

Key Points:
Left of -> → input parameters
Right of -> → code to run
Can have multiple parameters: (a, b) -> a + b
Can have a block of statements:
(x, y) -> {
    int sum = x + y;
    System.out.println(sum);
    return sum;
};
 */
import java.util.Arrays;
import java.util.List;

public class StreamLambdaExample {

    public static class Student {

        String name, course;

        Student(String name, String course) {
            this.name = name;
            this.course = course;
        }

        @Override
        public String toString() {
            return name + " - " + course;
        }
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("John", "Java"),
                new Student("Emma", "Python"),
                new Student("Liam", "Java")
        );
        //Filter a list of students by course
        System.out.println("Students in Java course:");
        students.stream()
                .filter(s -> s.course.equals("Java")) // lambda inside filter
                .forEach(System.out::println);       // lambda as method reference
        //Get names in uppercase
        System.out.println("Students Names in uppercase:");
        students.stream()
                .map(s -> s.name.toUpperCase()) // lambda transforms each element
                .forEach(System.out::println);
        //count students in Python course
        long pythonCount = students.stream()
                .filter(s -> s.course.equals("Python"))
                .count();

        System.out.println("Number of Python students: " + pythonCount);

        //combine all student names
        String allNames = students.stream()
                .map(s -> s.name)
                .reduce("", (a, b) -> a + ", " + b);

        System.out.println("All students: " + allNames.substring(2));
        // remove first comma and blank

    }
}
