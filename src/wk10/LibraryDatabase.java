package wk10;

import java.sql.*;
import java.util.Scanner;

public class LibraryDatabase {

    // =========================
    // DATABASE CONFIGURATION
    // =========================
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB = "library_management";
    private static final String DB_URL = SERVER_URL + DB;
    private static final String USER = "root";
    private static final String PASSWORD = "Jemmett1";

    private static final Scanner in = new Scanner(System.in);

    // =========================
    // CONNECTION MODEL (CLEANER APPROACH)
    // =========================
    static Connection getConnection(boolean server) throws SQLException {
        return DriverManager.getConnection(
                server ? SERVER_URL : DB_URL,
                USER,
                PASSWORD
        );
    }

    // =========================
    // SAFE EXECUTION HELPER (PreparedStatement)
    // =========================
    static void execUpdate(String sql, String msg, boolean server) {

        try (Connection c = getConnection(server); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.executeUpdate();
            System.out.println(msg);

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    // =========================
    // UTILITIES
    // =========================
    static void pause() {
        System.out.print("\nPress Enter to continue...");
        in.nextLine();
    }

    static boolean confirm(String warning) {
        System.out.println(warning);
        System.out.print("Type YES to continue: ");
        return in.nextLine().equalsIgnoreCase("YES");
    }

    static int readInt() {
        while (true) {
            try {
                System.out.print("Choice: ");
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    // =========================
    // DATABASE
    // =========================
    static void createDatabase() {
        execUpdate(
                "CREATE DATABASE IF NOT EXISTS " + DB,
                "Database ready.",
                true
        );
        pause();
    }

    // =========================
    // TABLES
    // =========================
    static void createBooksTable() {

        String sql = """
        CREATE TABLE IF NOT EXISTS books(
            book_id CHAR(8) PRIMARY KEY,
            title VARCHAR(60) NOT NULL,
            author VARCHAR(40) NOT NULL,
            price DECIMAL(6,2),
            publication_year INT
        )
        """;

        execUpdate(sql, "Books table created.", false);
        pause();
    }

    static void createMembersTable() {

        String sql = """
        CREATE TABLE IF NOT EXISTS members(
            member_id CHAR(8) PRIMARY KEY,
            member_name VARCHAR(50) NOT NULL,
            email VARCHAR(60) UNIQUE,
            join_date DATE
        )
        """;

        execUpdate(sql, "Members table created.", false);
        pause();
    }

    // =========================
    // ALTER TABLE
    // =========================
    static void addGenreColumn() {
        execUpdate(
                "ALTER TABLE books ADD genre VARCHAR(30)",
                "Genre added.",
                false
        );
        pause();
    }

    static void renamePublicationYear() {
        execUpdate(
                "ALTER TABLE books RENAME COLUMN publication_year TO published_year",
                "Column renamed.",
                false
        );
        pause();
    }

    static void modifyPriceColumn() {
        execUpdate(
                "ALTER TABLE books MODIFY price DECIMAL(8,2)",
                "Price modified.",
                false
        );
        pause();
    }

    static void dropGenreColumn() {
        if (!confirm("This will drop genre column")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate(
                "ALTER TABLE books DROP COLUMN genre",
                "Genre dropped.",
                false
        );
        pause();
    }

    // =========================
    // DATA OPERATIONS
    // =========================
    static void truncateBooksTable() {

        if (!confirm("This will delete ALL records in books")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate("TRUNCATE TABLE books", "Books truncated.", false);
        pause();
    }

    // =========================
    // DROP OPERATIONS
    // =========================
    static void dropBooksTable() {

        if (!confirm("Drop books table permanently?")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate("DROP TABLE IF EXISTS books", "Books dropped.", false);
        pause();
    }

    static void dropMembersTable() {

        if (!confirm("Drop members table permanently?")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate("DROP TABLE IF EXISTS members", "Members dropped.", false);
        pause();
    }

    static void dropDatabase() {

        if (!confirm("DROP ENTIRE DATABASE?")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate(
                "DROP DATABASE IF EXISTS " + DB,
                "Database dropped.",
                true
        );

        pause();
    }

    // =========================
    // VIEW METHODS (PreparedStatement SAFE SELECT)
    // =========================
    static void showTables() {

        System.out.println("\nTables:");

        try (Connection c = getConnection(false); PreparedStatement ps = c.prepareStatement("SHOW TABLES"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(" - " + rs.getString(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        pause();
    }

    static void describeBooksTable() {

        System.out.printf("%-20s %-20s%n", "Field", "Type");

        try (Connection c = getConnection(false); PreparedStatement ps = c.prepareStatement("DESCRIBE books"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("%-20s %-20s%n",
                        rs.getString("Field"),
                        rs.getString("Type"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        pause();
    }

    // =========================
    // MAIN MENU
    // =========================
    public static void main(String[] args) {

        int ch;

        do {
            System.out.println("""
==============================
 Library Database Setup
==============================
1.Create Database
2.Create Books Table
3.Create Members Table
4.Add Genre Column
5.Rename Publication Year
6.Modify Price Column
7.Drop Genre Column
8.Show Tables
9.Describe Books Table
10.Truncate Books Table
11.Drop Books Table
12.Drop Members Table
13.Drop Database
0.Exit
""");

            ch = readInt();

            switch (ch) {
                case 1 ->
                    createDatabase();
                case 2 ->
                    createBooksTable();
                case 3 ->
                    createMembersTable();
                case 4 ->
                    addGenreColumn();
                case 5 ->
                    renamePublicationYear();
                case 6 ->
                    modifyPriceColumn();
                case 7 ->
                    dropGenreColumn();
                case 8 ->
                    showTables();
                case 9 ->
                    describeBooksTable();
                case 10 ->
                    truncateBooksTable();
                case 11 ->
                    dropBooksTable();
                case 12 ->
                    dropMembersTable();
                case 13 ->
                    dropDatabase();
                case 0 ->
                    System.out.println("Goodbye.");
                default -> {
                    System.out.println("Invalid choice.");
                    pause();
                }
            }

        } while (ch != 0);

        in.close();
    }
}
