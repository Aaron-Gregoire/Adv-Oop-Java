package wk9;/*Notes:
Statement -You build the SQL as a complete string yourself.
Vulnerable to SQL injection if user input is concatenated.
Database must parse and compile the SQL every time it runs.
Can be messy when values come from variables. 
SQL injection occurs when a program builds SQL by concatenating user input directly into the SQL string.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/georgian_college?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";          // your MySQL username
        String password = "Jemmett1";

        try {
            // Force loading the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully!");

            DatabaseMetaData dbMetaData = conn.getMetaData();
            System.out.println("Database Product Name: " + dbMetaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + dbMetaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + dbMetaData.getDriverName());
            System.out.println("Driver Version: " + dbMetaData.getDriverVersion());

            // Check if the database engine supports transactions
            boolean supportsTx = dbMetaData.supportsTransactions();
            System.out.println("Supports Transactions: " + supportsTx);
            // Check if a specific transaction isolation level is supported
            boolean supportsSerializable = dbMetaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_SERIALIZABLE
            );
            System.out.println("Supports Transaction Serializable: " + supportsSerializable);
            // Check if batch updates are supported
            boolean supportsBatch = dbMetaData.supportsBatchUpdates();

            System.out.println("Supports Batch: " + supportsBatch);
            // Parameters: catalog, schemaPattern, tableNamePattern, types[]
            // Passing null or "%" acts as a wildcard filter
            ResultSet rs1 = dbMetaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (rs1.next()) {
                // The 3rd column in the returned ResultSet contains the actual Table Name
                String tableName = rs1.getString("TABLE_NAME");
                System.out.println("Found Table: " + tableName);
            }
            rs1.close();

            // Example query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");
            if (rs.next()) {
                System.out.println("Database time: " + rs.getString(1));
            }

            conn.close(); // automatically closes statements and result sets
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Did you add the JAR to lib/?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check URL, username, and password.");
            e.printStackTrace();
        }
    }
}
