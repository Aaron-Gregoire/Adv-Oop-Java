package wk10;

import java.sql.*;
import java.util.Scanner;

public class LibraryDatabaseAssignment {

    // =========================
    // DATABASE CONFIGURATION
    // =========================
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB = "library_management_assignment";
    private static final String DB_URL = SERVER_URL + DB;
    private static final String USER = "root";
    private static final String PASSWORD = "jemmett1";

    private static final Scanner in = new Scanner(System.in);

    // =========================
    // CONNECTION MODEL
    // =========================
    static Connection getConnection(boolean server) throws SQLException {
        return DriverManager.getConnection(
                server ? SERVER_URL : DB_URL,
                USER,
                PASSWORD
        );
    }

    // =========================
    // EXECUTOR
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
    // HELPER METHODS
    // =========================
    static void pause() {
        System.out.print("\nPress Enter to continue...");
        in.nextLine();
    }

    static boolean confirm(String msg) {
        System.out.println(msg);
        System.out.print("Type YES to confirm: ");
        return in.nextLine().equalsIgnoreCase("YES");
    }

    static int readInt() {
        while (true) {
            try {
                System.out.print("Choice: ");
                return Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number.");
            }
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    // =========================
    // DATABASE OPERATIONS
    // =========================
    static void createDatabase() {
        execUpdate("CREATE DATABASE IF NOT EXISTS " + DB,
                "Database ready.", true);
        pause();
    }

    // =========================
    // TABLES
    // =========================
    static void createBooksTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS books(
            book_id CHAR(8) PRIMARY KEY,
            title VARCHAR(60),
            author VARCHAR(40),
            price DECIMAL(8,2),
            published_year INT
        )
        """;

        execUpdate(sql, "Books table created.", false);
        pause();
    }

    static void createMembersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS members(
            member_id CHAR(8) PRIMARY KEY,
            member_name VARCHAR(50),
            email VARCHAR(60)
        )
        """;

        execUpdate(sql, "Members table created.", false);
        pause();
    }

    // =========================
    // PART A: LOANS TABLE
    // =========================
    static void createLoansTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS loans(
            loan_id CHAR(8) PRIMARY KEY,
            book_id CHAR(8),
            member_id CHAR(8),
            loan_date DATE,
            return_date DATE,
            FOREIGN KEY (book_id) REFERENCES books(book_id),
            FOREIGN KEY (member_id) REFERENCES members(member_id)
        )
        """;

        execUpdate(sql, "Loans table created.", false);
        pause();
    }

    static void dropLoansTable() {
        if (!confirm("This will permanently delete the loans table and all its data.")) {
            System.out.println("Cancelled.");
            pause();
            return;
        }

        execUpdate("DROP TABLE IF EXISTS loans", "Loans table dropped.", false);
        pause();
    }

    // =========================
    // PART B: INSERT OPERATIONS
    // =========================
    static void addBook() {
        String id = readLine("Book ID: ");
        String title = readLine("Title: ");
        String author = readLine("Author: ");
        double price = Double.parseDouble(readLine("Price: "));
        int year = Integer.parseInt(readLine("Published Year: "));

        String sql = "INSERT INTO books(book_id, title, author, price, published_year) VALUES (?,?,?,?,?)";

        try (Connection c = getConnection(false); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setDouble(4, price);
            ps.setInt(5, year);
            ps.executeUpdate();
            System.out.println("Book added.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        pause();
    }

    static void addMember() {
        String id = readLine("Member ID: ");
        String name = readLine("Member Name: ");
        String email = readLine("Email: ");

        String sql = "INSERT INTO members(member_id, member_name, email) VALUES (?,?,?)";

        try (Connection c = getConnection(false); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.executeUpdate();
            System.out.println("Member added.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        pause();
    }

    static void issueBook() {
        String loanId = readLine("Loan ID: ");
        String bookId = readLine("Book ID: ");
        String memberId = readLine("Member ID: ");
        String loanDate = readLine("Loan Date (YYYY-MM-DD): ");
        String returnDate = readLine("Return Date (YYYY-MM-DD, leave blank if not yet returned): ");

        String sql = "INSERT INTO loans(loan_id, book_id, member_id, loan_date, return_date) VALUES (?,?,?,?,?)";

        try (Connection c = getConnection(false); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loanId);
            ps.setString(2, bookId);
            ps.setString(3, memberId);
            ps.setDate(4, Date.valueOf(loanDate));

            if (returnDate.isBlank()) {
                ps.setNull(5, Types.DATE);
            } else {
                ps.setDate(5, Date.valueOf(returnDate));
            }

            ps.executeUpdate();
            System.out.println("Book issued.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        pause();
    }

    // =========================
    // PART C: SELECT OPERATIONS
    // =========================
    static void viewBooks() {
        String sql = "SELECT book_id, title, author, price, published_year FROM books";

        try (Connection c = getConnection(false);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID       Title                          Author              Price     Year");
            while (rs.next()) {
                System.out.printf("%-8s %-30s %-19s %-9.2f %d%n",
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("published_year"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        pause();
    }

    static void viewMembers() {
        String sql = "SELECT member_id, member_name, email FROM members";

        try (Connection c = getConnection(false);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID       Name                Email");
            while (rs.next()) {
                System.out.printf("%-8s %-19s %s%n",
                        rs.getString("member_id"),
                        rs.getString("member_name"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        pause();
    }

    static void viewLoans() {
        String sql = """
        SELECT l.loan_id, b.title, m.member_name, l.loan_date, l.return_date
        FROM loans l
        JOIN books b ON l.book_id = b.book_id
        JOIN members m ON l.member_id = m.member_id
        """;

        try (Connection c = getConnection(false);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nLoanID   Title                          Member              LoanDate     ReturnDate");
            while (rs.next()) {
                Date returned = rs.getDate("return_date");
                System.out.printf("%-8s %-30s %-19s %-12s %s%n",
                        rs.getString("loan_id"),
                        rs.getString("title"),
                        rs.getString("member_name"),
                        rs.getDate("loan_date"),
                        returned == null ? "-- not returned --" : returned.toString());
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
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
=========================
 LIBRARY SYSTEM
=========================
1 Create Database
2 Create Books Table
3 Create Members Table
4 Create Loans Table
5 Drop Loans Table
6 Add Book
7 Add Member
8 Issue Book
9 View Books
10 View Members
11 View Loans
0 Exit
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
                        createLoansTable();
                case 5 ->
                        dropLoansTable();
                case 6 ->
                        addBook();
                case 7 ->
                        addMember();
                case 8 ->
                        issueBook();
                case 9 ->
                        viewBooks();
                case 10 ->
                        viewMembers();
                case 11 ->
                        viewLoans();
                case 0 ->
                        System.out.println("Goodbye");
                default ->
                        System.out.println("Invalid choice");
            }

        } while (ch != 0);
    }
}