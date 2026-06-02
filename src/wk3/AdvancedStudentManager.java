package wk3;/*
The purpose of this program is to create a console-based Student Management System that demonstrates modern Java features such as Streams, Lambdas, Collectors, and Switch Expressions. 
 */
import java.util.*;
import java.util.stream.Collectors;

public class AdvancedStudentManager {

    // ===== MODEL CLASS =====
    public static class Student {

        private String name;
        private String course;

        public Student(String name, String course) {
            this.name = name;
            this.course = course;
        }

        public String getName() {
            return name;
        }

        public String getCourse() {
            return course;
        }

        @Override
        public String toString() {
            return name + " - " + course;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        // Prepopulate some students
        students.add(new Student("John", "Java"));
        students.add(new Student("Emma", "Python"));
        students.add(new Student("Liam", "Java"));
        students.add(new Student("Sophia", "Web Dev"));

        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Advanced Student Manager ---");
            System.out.println("1. List all students and their course");
            System.out.println("2. Add a student");
            System.out.println("3. Filter students by course");
            System.out.println("4. Count students per course");
            System.out.println("5. List all student names uppercase");
            System.out.println("6. Combine all student names into a single string");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            /*
            switch (choice) {
                case "1":
                   
                    // List all students using forEach + lambda
                    System.out.println("\nAll students and their course:");
                    students.forEach(s -> System.out.println(s));
                    break;

                case "2":
                    // Add a student
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter course: ");
                    String course = scanner.nextLine().trim();

                    // Prevent duplicates (same name + course)
                    boolean exists = students.stream()
                            .anyMatch(s -> s.getName().equalsIgnoreCase(name) &&
                                           s.getCourse().equalsIgnoreCase(course));
                    if (exists) {
                        System.out.println("Student with same name and course already exists!");
                    } else if (name.isBlank() || course.isBlank()) {
                        System.out.println("Name or course cannot be empty!");
                    } else {
                        students.add(new Student(name, course));
                        System.out.println("Student added!");
                    }
                    break;

                case "3":
                    System.out.print("Enter course to filter: ");
                    String filterCourse = scanner.nextLine().trim();

                                      // Get students in that course
                        List<Student> filtered = students.stream()
                                .filter(s -> s.getCourse().equalsIgnoreCase(filterCourse))
                                .collect(Collectors.toList());
                        if (filtered.isEmpty()) {
                            System.out.println("No students enrolled in course: " + filterCourse);
                        } else {
                            System.out.println("Students in " + filterCourse + ":");
                            filtered.forEach(System.out::println);
                        }
                    }
                    break;

                case "4":
                    // Count students per course
                  
                    System.out.println("Student counts per course:");
                    Map<String, Long> counts = students.stream()
                            .collect(Collectors.groupingBy(
                                    Student::getCourse, Collectors.counting()));
                    counts.forEach((c, count) -> System.out.println(c + ": " + count));
                    break;

                case "5":
                    // Names uppercase
                    
                    System.out.println("Student names in uppercase:");
                    students.stream()
                            .map(s -> s.getName().toUpperCase())
                            .forEach(System.out::println);
                    break;

                case "6":
                    // Combine all names into single string
                    
                    String combined = students.stream()
                            .map(Student::getName)
                            .collect(Collectors.joining(", "));
                    System.out.println("All students: " + combined);
                    break;

                case "7":
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option! Try again.");
            }
             */
 /*
 Rule Switch key Points
-> replaces : and break.
yield is used if you need a multi-line block that returns a value.
Cleaner, shorter, and safer — no accidental fall-through.
Works perfectly with String menus or enums.
             */

            String action = switch (choice) {
                case "1" -> {
                    System.out.println("\nAll students and their course:");
                    students.forEach(s -> System.out.println(s));
                    yield ""; // no message to return
                }

                case "2" -> {
                    // Add a student
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter course: ");
                    String course = scanner.nextLine().trim();

                    if (name.isBlank() || course.isBlank()) {
                        System.out.println("Name or course cannot be empty!");
                    } else {
                        // Prevent duplicates (same name + course)
                        boolean exists = students.stream()
                                .anyMatch(s -> s.getName().equalsIgnoreCase(name)
                                && s.getCourse().equalsIgnoreCase(course));
                        if (exists) {
                            System.out.println("Student with same name and course already exists!Cannot add the student.");
                        } else {
                            students.add(new Student(name, course));
                            System.out.println("Student added!");
                        }
                    }
                    yield ""; // block must yield a value
                }

                case "3" -> {
                    System.out.print("Enter course to filter: ");
                    String filterCourse = scanner.nextLine().trim();
                    //check if filterCourse is provided
                    if (filterCourse.isEmpty()) {
                        System.out.println("Course cannot be empty");
                    } else {
                        // Get students in that course
                        List<Student> filtered = students.stream()
                                .filter(s -> s.getCourse().equalsIgnoreCase(filterCourse))
                                .collect(Collectors.toList());
                        if (filtered.isEmpty()) {
                            System.out.println("No students enrolled in the given course: " + filterCourse);
                        } else {
                            System.out.println("Students in " + filterCourse + ":");
                            filtered.forEach(System.out::println);
                        }
                    }
                    yield "";
                }

                case "4" -> {

                    System.out.println("Student counts per course:");
                    Map<String, Long> counts = students.stream()
                            .collect(Collectors.groupingBy(
                                    Student::getCourse, Collectors.counting()));
                    counts.forEach((c, count) -> System.out.println(c + ": " + count));
                    yield "";
                }

                case "5" -> {

                    System.out.println("Student names in uppercase:");
                    students.stream()
                            .map(s -> s.getName().toUpperCase())
                            .forEach(System.out::println);
                    yield "";
                }

                case "6" -> {

                    String combined = students.stream()
                            .map(Student::getName)
                            .collect(Collectors.joining(", "));
                    System.out.println("All students: " + combined);
                    yield "";
                }

                case "7" -> {
                    exit = true;
                    yield "Exiting program...";
                }

                default ->
                    "Invalid option! Try again."; // no yield needed since an expression and is automatically returned; not a block
            };

// Print result if there is a message; action is assigned what yield returns
            if (!action.isEmpty()) {
                System.out.println(action);
            }
        }

        System.out.println(
                "Goodbye!");
        scanner.close();
    }
}

/* 
Scope of improvement-Almost every case returns "", which is only there to satisfy the switch expression requirement. That makes the code harder to read.So we needn't use 'a switch expression that returns a value' and hence yield is not needed.

So use a 'switch expression that does not return a value', so no yield needed. Code individual methods, each doing a 'job' a

switch (choice) {
    case "1" -> listStudents(students);

    case "2" -> addStudent(scanner, students);

    case "3" -> filterStudents(scanner, students);

    case "4" -> countStudents(students);

    case "5" -> displayUppercase(students);

    case "6" -> combineNames(students);

    case "7" -> {
        exit = true;
        System.out.println("Exiting program...");
    }

    default -> System.out.println("Invalid option!");
}
 */
