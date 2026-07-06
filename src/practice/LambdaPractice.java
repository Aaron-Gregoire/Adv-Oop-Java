package practice;

import java.util.Comparator;
import java.util.List;

public class LambdaPractice {

    // 1.3 - custom functional interface, don't change this
    interface Validator {
        boolean isValid(Student s);
    }

    public static void main(String[] args) throws InterruptedException {

        List<Student> students = List.of(
                new Student("John", "Java", 85),
                new Student("Emma", "Python", 92),
                new Student("Liam", "Java", 78),
                new Student("Sophia", "Web Dev", 88),
                new Student("Noah", "Python", 65),
                new Student("Ava", "Java", 91)
        );

        System.out.println("--- 1.1: sort students by grade, highest first ---");
        // TODO: write a Comparator<Student> lambda called byGradeDesc
        Comparator<Student> byGradeDesc = (a, b) -> b.getGrade() - a.getGrade();
        students.stream().sorted(byGradeDesc).forEach(System.out::println);


        System.out.println("\n--- 1.2: Runnable that prints \"Task running\", run in a Thread ---");
        // TODO: write a Runnable lambda, wrap it in a Thread, start it, and join it.
        Runnable task = () -> System.out.println("Task running");
        Thread t = new Thread(task);
        t.start();
        t.join(); // wait for it to finish so output stays in order

        System.out.println("\n--- 1.3: custom Validator lambda ---");
        // TODO: write a lambda implementing Validator that checks grade >= 70
        Validator passing = s -> s.getGrade() >= 70;
        for (Student s : students) {
            System.out.println(s.getName() + " passing? " + passing.isValid(s));
        }

        System.out.println("\n--- 1.4: lambda vs method reference ---");
        // TODO: print each name using a lambda
        // TODO: print each name again using a method reference instead
        List<String> names = List.of("John", "Emma", "Liam");
        names.forEach(n -> System.out.println(n));   // lambda
        names.forEach(System.out::println);          // method reference
    }
}
