package wk9;

import java.sql.*;

public class RSMetaDataExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/georgian_college?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "Jemmett1";

        String sql = "SELECT * FROM employee_information"; // Can be any query on any table

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // 1. Get the MetaData object from the ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // 2. Discover how many columns were returned
            int columnCount = rsmd.getColumnCount();
            System.out.println("Total Columns: " + columnCount);
            System.out.println("----------------------------------------");

            // 3. Loop through and print out information about each column
            // Note: JDBC indexes always start at 1, not 0
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                String columnType = rsmd.getColumnTypeName(i);

                System.out.println("Column #" + i + ": " + columnName + " (Type: " + columnType + ")");
            }
            System.out.println("----------------------------------------");

            // 4. Now process the actual row data normally
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    // Dynamically pull data based on the column loop index
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
