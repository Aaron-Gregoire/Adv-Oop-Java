package practice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamPractice {

    public static void main(String[] args) {

        List<Student> students = List.of(
                new Student("John", "Java", 85),
                new Student("Emma", "Python", 92),
                new Student("Liam", "Java", 78),
                new Student("Sophia", "Web Dev", 88),
                new Student("Noah", "Python", 65),
                new Student("Ava", "Java", 91)
        );

        System.out.println("--- 2.1: Java students, sorted by name ---");
        // TODO: filter students in "Java", sort by name, print each one
        students.stream()
                        .filter(s -> s.getCourse().equals("Java"))
                        .sorted(Comparator.comparing(Student::getName))
                        .forEach(System.out::println);


        System.out.println("\n--- 2.2: average grade ---");
        // TODO: compute the average grade of all students and print it
        // hint: students.stream().mapToInt(...).average() returns an OptionalDouble
        double avg = students.stream()
                        .mapToInt(Student::getGrade)
                        .average()
                        .orElse(0);
        System.out.println(avg);

        System.out.println("\n--- 2.3: top student ---");
        // TODO: find the student with the highest grade and print their name
        // hint: .max(Comparator.comparing(...)) returns an Optional<Student>
        Optional<Student> top = students.stream()
                        .max(Comparator.comparing(Student::getGrade));
        top.ifPresent(s-> System.out.println(s.getName()));

        System.out.println("\n--- 2.4: grouped by course ---");
        // TODO: group students into a Map<String, List<Student>> keyed by course
        // then print each course and its list of students
        Map<String, List<Student>> byCourse = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse));
        byCourse.forEach((course, list) -> System.out.println(course + " -> " + list));

        System.out.println("\n--- 2.5: students above 80 ---");
        // TODO: build a single comma-separated string of names of students
        // who scored above 80, and print it
        String names = students.stream()
                .filter(s -> s.getGrade() > 80)
                .map(Student::getName)
                .collect(Collectors.joining(", "));
        System.out.println(names);

        System.out.println("\n--- 2.6: distinct course count ---");
        // TODO: count how many distinct courses exist and print it
        long distinctCourses = students.stream()
                .map(Student::getCourse)
                .distinct()
                .count();
        System.out.println(distinctCourses);
    }
}
