package wk9;

import java.sql.Date;
import java.util.ArrayList;

public class EmployeeDatabaseDemo {

    public static void main(String[] args) {

        EmployeeDAO dao = new EmployeeDAO();

        Employee emp = new Employee(
                "E1001",
                "John Smith",
                "john.smith@georgiancollege.ca",
                Date.valueOf("2021-09-01"),
                5,
                75000,
                "Faculty"
        );

        // Insert
        dao.insertEmployee(emp);

        // Search
        Employee found = dao.searchEmployee("E1001");

        if (found != null) {
            System.out.println("\nEmployee found:");
            System.out.println(found);
        }

        // Update
        emp.setAnnualSalary(85000);
        dao.updateEmployee(emp);

        // Display all
        System.out.println("\nAll Employees:");

        ArrayList<Employee> employees = dao.getAllEmployees();

        for (Employee e : employees) {
            System.out.println(e);
        }

        // Delete
        dao.deleteEmployee("E1001");
    }
}
