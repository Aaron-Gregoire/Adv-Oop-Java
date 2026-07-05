package wk9;

import java.sql.Date;

public class Employee {

    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private Date hireDate;
    private int yearsInJob;
    private double annualSalary;
    private String employeeType;

    public Employee(String employeeId,
            String employeeName,
            String employeeEmail,
            Date hireDate,
            int yearsInJob,
            double annualSalary,
            String employeeType) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.hireDate = hireDate;
        this.yearsInJob = yearsInJob;
        this.annualSalary = annualSalary;
        this.employeeType = employeeType;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public int getYearsInJob() {
        return yearsInJob;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public void setYearsInJob(int yearsInJob) {
        this.yearsInJob = yearsInJob;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    @Override
    public String toString() {
        return String.format(
                "%-10s %-20s %-30s %-12s %-3d %-10.2f %-20s",
                employeeId,
                employeeName,
                employeeEmail,
                hireDate,
                yearsInJob,
                annualSalary,
                employeeType);
    }
}
