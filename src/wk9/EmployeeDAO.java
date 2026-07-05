package wk9;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {

    private static final String URL
            = "jdbc:mysql://localhost:3306/georgian_college";

    private static final String USER = "root";
    private static final String PASSWORD = "Jemmett1";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // INSERT
    public void insertEmployee(Employee emp) {

        String sql = """
                INSERT INTO employee_information
(employee_id,
 employee_name,
 employee_email,
 hire_date,
 years_in_job,
 annual_salary,
 employee_type)
VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getEmployeeId());
            stmt.setString(2, emp.getEmployeeName());
            stmt.setString(3, emp.getEmployeeEmail());
            stmt.setDate(4, emp.getHireDate());
            stmt.setInt(5, emp.getYearsInJob());
            stmt.setDouble(6, emp.getAnnualSalary());
            stmt.setString(7, emp.getEmployeeType());

            int rows = stmt.executeUpdate();

            System.out.println(rows + " employee inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEARCH
    public Employee searchEmployee(String employeeId) {

        String sql
                = "SELECT * FROM employee_information WHERE employee_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employeeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Employee(
                        rs.getString("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_email"),
                        rs.getDate("hire_date"),
                        rs.getInt("years_in_job"),
                        rs.getDouble("annual_salary"),
                        rs.getString("employee_type")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // DISPLAY ALL
    public ArrayList<Employee> getAllEmployees() {

        ArrayList<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee_information";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Employee emp = new Employee(
                        rs.getString("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_email"),
                        rs.getDate("hire_date"),
                        rs.getInt("years_in_job"),
                        rs.getDouble("annual_salary"),
                        rs.getString("employee_type")
                );

                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // UPDATE
    public void updateEmployee(Employee emp) {

        String sql = """
                UPDATE employee_information
                SET employee_name = ?,
                    employee_email = ?,
                    hire_date = ?,
                    years_in_job = ?,
                    annual_salary = ?,
                    employee_type = ?
                WHERE employee_id = ?
                """;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getEmployeeName());
            stmt.setString(2, emp.getEmployeeEmail());
            stmt.setDate(3, emp.getHireDate());
            stmt.setInt(4, emp.getYearsInJob());
            stmt.setDouble(5, emp.getAnnualSalary());
            stmt.setString(6, emp.getEmployeeType());
            stmt.setString(7, emp.getEmployeeId());

            int rows = stmt.executeUpdate();

            System.out.println(rows + " employee updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteEmployee(String employeeId) {

        String sql
                = "DELETE FROM employee_information WHERE employee_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employeeId);

            int rows = stmt.executeUpdate();

            System.out.println(rows + " employee deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    public void insertTwoEmployees(Employee e1, Employee e2) {

    String sql = "INSERT INTO employee_information VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        conn.setAutoCommit(false);  

        // Employee 1
        stmt.setString(1, e1.getEmployeeId());
        stmt.setString(2, e1.getEmployeeName());
        stmt.setString(3, e1.getEmployeeEmail());
        stmt.setDate(4, e1.getHireDate());
        stmt.setInt(5, e1.getYearsInJob());
        stmt.setDouble(6, e1.getAnnualSalary());
        stmt.setString(7, e1.getEmployeeType());
        stmt.executeUpdate();

        // Employee 2
        stmt.setString(1, e2.getEmployeeId());
        stmt.setString(2, e2.getEmployeeName());
        stmt.setString(3, e2.getEmployeeEmail());
        stmt.setDate(4, e2.getHireDate());
        stmt.setInt(5, e2.getYearsInJob());
        stmt.setDouble(6, e2.getAnnualSalary());
        stmt.setString(7, e2.getEmployeeType());
        stmt.executeUpdate();

        conn.commit(); // ✔ save both

        System.out.println("Transaction successful");

    } catch (SQLException e) {

        try {
            System.out.println("Error occurred → rolling back");
            conn.rollback(); // ❌ undo everything
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}*/
}
