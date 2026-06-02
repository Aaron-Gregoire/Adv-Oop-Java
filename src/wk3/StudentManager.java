package wk3;/*
Java 16+ allows you to create a student record instead of a traditional class- the fields of a record are immutable, that is cannot be changed after an object is created, they are private and final. Cannot extend a record(treat it as final class). But a record can implement an interface
Automatically creates:
1. constructor
2. accessor methods (name(), course())
3. toString() , can customize by @Override
    public String toString() {
        return name + " - " + course;
    }
4. equals()
5. hashCode()
No setter methods are available
You can add methods to a record like you do in a class
Cannot add instance fields, though static fields can be added
You can add a compact or canonical(full) constructor to the class
Overloaded constructors can be added too
Example of compact constructor which modifies the default constructor
public record Student(String name, String course) {

    public Student {
        if (name.isBlank()) {
            throw new IllegalArgumentException(
                "Name cannot be empty");
        }
        course = course.toUpperCase(); // can customize fields, no this.
    }
}
Example of a canonical/full constructor
The parameter list must exactly match the record components.
public record Student(String name, String course) {

    public Student(String name, String course) {

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                "Name cannot be empty");
        }

        this.name = name;
        this.course = course;
    }
}

Rules are :
0 constructors → Java generates canonical constructor
Compact constructor → customize generated canonical constructor
Full canonical constructor → write the whole canonical constructor yourself
Overloaded constructors → add as many as needed
Compact + full canonical together → not allowed
Two records with same values are considered equal. In classs, we usually have to write equals
Student s1 = new Student("John", "Java");
Student s2 = new Student("John", "Java");
System.out.println(s1.equals(s2)); //returns true
 */

import java.util.*;
import java.util.stream.Collectors;

public class StudentManager {

    // Record replaces the whole Student class
    public record Student(String name, String course) {

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Student> students = new ArrayList<>();

        // Initial students
        students.add(new Student("John", "Java"));
        students.add(new Student("Emma", "Python"));
        students.add(new Student("Liam", "Java"));
        students.add(new Student("Sophia", "Web Dev"));

        boolean exit = false;

        while (!exit) {

            System.out.println("\n--- Student Manager ---");
            System.out.println("1. List all students");
            System.out.println("2. Add student");
            System.out.println("3. Filter by course");
            System.out.println("4. Count students per course");
            System.out.println("5. Display names uppercase");
            System.out.println("6. Combine names");
            System.out.println("7. Exit");

            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1" ->
                    listStudents(students);

                case "2" ->
                    addStudent(scanner, students);

                case "3" ->
                    filterStudents(scanner, students);

                case "4" ->
                    countStudents(students);

                case "5" ->
                    displayUppercase(students);

                case "6" ->
                    combineNames(students);

                case "7" -> {
                    exit = true;
                    System.out.println("Exiting...");
                }

                default ->
                    System.out.println("Invalid option!");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void listStudents(List<Student> students) {

        System.out.println("\nStudents:");

        students.forEach(System.out::println);
    }

    private static void addStudent(
            Scanner scanner,
            List<Student> students) {

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter course: ");
        String course = scanner.nextLine().trim();

        if (name.isBlank() || course.isBlank()) {
            System.out.println("Fields cannot be empty");
            return;
        }

        boolean exists = students.stream()
                .anyMatch(s
                        -> s.name().equalsIgnoreCase(name)
                && s.course().equalsIgnoreCase(course));

        if (exists) {
            System.out.println("Student already exists");
            return;
        }

        students.add(new Student(name, course));

        System.out.println("Student added");
    }

    private static void filterStudents(
            Scanner scanner,
            List<Student> students) {

        System.out.print("Enter course: ");

        String course = scanner.nextLine();
        if (course.isEmpty()) {
            System.out.println("Course cannot be empty");
        } else {
            List<Student> filtered
                    = students.stream()
                            .filter(s
                                    -> s.course()
                                    .equalsIgnoreCase(course))
                            .toList();

            if (filtered.isEmpty()) {
                System.out.println("No students found");
            } else {
                filtered.forEach(System.out::println);
            }
        }
    }

    private static void countStudents(
            List<Student> students) {

        Map<String, Long> counts
                = students.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Student::course,
                                        Collectors.counting()
                                ));

        counts.forEach(
                (course, count)
                -> System.out.println(
                        course + ": " + count));
    }

    private static void displayUppercase(
            List<Student> students) {

        students.stream()
                .map(s -> s.name().toUpperCase())
                .forEach(System.out::println);
    }

    private static void combineNames(
            List<Student> students) {

        String names
                = students.stream()
                        .map(Student::name)
                        .collect(Collectors.joining(", "));

        System.out.println(names);
    }
}
