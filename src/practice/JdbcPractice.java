package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// NOTE: compiles fine with just the JDK (java.sql is standard library).
// To actually RUN insert/select/update against MySQL you need the mysql connector
// jar on your classpath, a real "students" table, and correct URL/USER/PASSWORD below.
// Table used: students(student_id VARCHAR, name VARCHAR, course VARCHAR, grade INT)
public class JdbcPractice {

    private static final String URL = "jdbc:mysql://localhost:3306/georgian_college";
    private static final String USER = "root";
    private static final String PASSWORD = "Jemmett1";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 4.1 - insert
    public void insertStudent(Student s, String id) {
        String sql = "INSERT INTO students (student_id, name, course, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, s.getName());
            stmt.setString(3, s.getCourse());
            stmt.setInt(4, s.getGrade());

            int rows = stmt.executeUpdate();
            System.out.println(rows + " student(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4.2 - select by course
    public ArrayList<Student> getStudentsByCourse(String course) {
        ArrayList<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE course = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(new Student(
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("grade")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 4.3 - update grade
    public void updateGrade(String studentId, int newGrade) {
        String sql = "UPDATE students SET grade = ? WHERE student_id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newGrade);
            stmt.setString(2, studentId);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4.4/4.5 - transaction: two inserts, roll back both if either fails
    public void insertTwoStudentsTransactionally(Student s1, String id1, Student s2, String id2) {
        String sql = "INSERT INTO students (student_id, name, course, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, id1);
                stmt.setString(2, s1.getName());
                stmt.setString(3, s1.getCourse());
                stmt.setInt(4, s1.getGrade());
                stmt.executeUpdate();

                stmt.setString(1, id2);
                stmt.setString(2, s2.getName());
                stmt.setString(3, s2.getCourse());
                stmt.setInt(4, s2.getGrade());
                stmt.executeUpdate();

                conn.commit();
                System.out.println("Transaction committed - both students saved.");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error occurred - rolled back, nothing saved.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4.6 - print column names/types without knowing the table structure in advance
    public void printColumnMetadata(String tableName) {
        String sql = "SELECT * FROM " + tableName;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.println(rsmd.getColumnName(i) + " (" + rsmd.getColumnTypeName(i) + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JdbcPractice dao = new JdbcPractice();

        // Uncomment these once your DB connection details above are correct
        // and the "students" table exists:

        // dao.insertStudent(new Student("John", "Java", 85), "S1001");
        // ArrayList<Student> javaStudents = dao.getStudentsByCourse("Java");
        // javaStudents.forEach(System.out::println);
        // dao.updateGrade("S1001", 90);
        // dao.insertTwoStudentsTransactionally(
        //         new Student("Emma", "Python", 92), "S1002",
        //         new Student("Liam", "Java", 78), "S1003"
        // );
        // dao.printColumnMetadata("students");
    }
}

