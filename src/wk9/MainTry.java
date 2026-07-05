package wk9;/*Notes:
Statement -You build the SQL as a complete string yourself.
Vulnerable to SQL injection if user input is concatenated.
Database must parse and compile the SQL every time it runs.
Can be messy when values come from variables. 
SQL injection occurs when a program builds SQL by concatenating user input directly into the SQL string.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainTry {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/georgian_college?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";          // your MySQL username
        String password = "Jemmett1";

        try {
            //is used to dynamically load and register the MySQL database driver into your Java
            // application's memory at runtime.
            //Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully!");

            // Example query
            Statement stmt = conn.createStatement();
            //use execute if do not know the 'type' of the DML, can be select or update ot insert ot delete
            boolean hasResultSet = stmt.execute("SELECT NOW()");
            //hasResultSet is true if it was a SELECT statement
            if (hasResultSet) {
                System.out.println("has result set");
                // The SQL was a SELECT query
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        System.out.println("Database time: " + rs.getString(1));
                    } else {
                        // The SQL was a DML statement (INSERT, UPDATE, DELETE)
                        int count = stmt.getUpdateCount();
                        if (count != -1) {
                            System.out.println("Rows affected: " + count);
                        } else {
                            System.out.println("The statement was a DDL command (like CREATE/DROP) with no update count.");
                        }
                    }
                }
            }

            conn.close();
        } //catch (ClassNotFoundException e) {
        //  System.out.println("MySQL JDBC Driver not found. Did you add the JAR to lib/?");
        //  e.printStackTrace();
        //}
        catch (SQLException e) {
            System.out.println("Connection failed. Check URL, username, and password.");
            e.printStackTrace();
        }
    }
}
