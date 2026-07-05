package wk9;

import java.sql.*;

public class TransactionDemo {

    private static final String URL
            = "jdbc:mysql://localhost:3306/georgian_college";
    private static final String USER = "root";
    private static final String PASSWORD = "Jemmett1";

    public static void main(String[] args) {

        // Change this to TRUE or FALSE to compare behaviour
        boolean useTransaction = true;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Connected to DB");

            conn.setAutoCommit(!useTransaction);

            System.out.println("AutoCommit = " + conn.getAutoCommit());

            if (useTransaction) {
                runTransaction(conn);
            } else {
                runAutoCommit(conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // AUTO COMMIT DEMO
    // =========================
    public static void runAutoCommit(Connection conn) throws SQLException {

        System.out.println("\n--- AUTO COMMIT MODE ---");

        insertEmployee(conn, "A1001", "John Auto");
        insertEmployee(conn, "A1002", "Jane Auto");

        // even if error happens later, first insert is already saved
        System.out.println("AutoCommit mode: data saved immediately");
    }

    // =========================
    // TRANSACTION DEMO
    // =========================
    public static void runTransaction(Connection conn) throws SQLException {

        System.out.println("\n--- TRANSACTION MODE ---");

        try {

            insertEmployee(conn, "T1001", "John TX");

            // FORCE ERROR (duplicate primary key OR invalid data)
            insertEmployee(conn, "T1001", "Duplicate TX");

            conn.commit();
            System.out.println("Transaction committed");

        } catch (SQLException e) {

            System.out.println("Error occurred → ROLLBACK");

            conn.rollback();

            System.out.println("Transaction rolled back (nothing saved)");
        }
    }

    // =========================
    // INSERT METHOD
    // =========================
    public static void insertEmployee(Connection conn,
            String id,
            String name) throws SQLException {

        String sql = """
                INSERT INTO employee_information
                (employee_id, employee_name, employee_email,
                 hire_date, years_in_job, annual_salary, employee_type)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, name.toLowerCase() + "@test.com");
            stmt.setDate(4, Date.valueOf("2024-01-01"));
            stmt.setInt(5, 1);
            stmt.setDouble(6, 60000);
            stmt.setString(7, "Faculty");

            stmt.executeUpdate();

            System.out.println("Inserted: " + id);

        }
    }
}
