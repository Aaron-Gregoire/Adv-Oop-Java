package practice;

public class Student {

    private String name;
    private String course;
    private int grade;

    public Student(String name, String course, int grade) {
        this.name = name;
        this.course = course;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return name + " - " + course + " (" + grade + ")";
    }
}
