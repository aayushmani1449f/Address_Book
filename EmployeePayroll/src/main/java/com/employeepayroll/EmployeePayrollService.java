package com.employeepayroll;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class EmployeePayrollService {

    private EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public static void main(String[] args) {
        EmployeePayrollService service = new EmployeePayrollService();
        service.testConnection();
        
        System.out.println("---------- UC 2: Read Data ----------");
        List<EmployeePayrollData> employeePayrollData = service.readEmployeePayrollData();
        employeePayrollData.forEach(System.out::println);

        System.out.println("---------- UC 5: Retrieve by Date Range ----------");
        List<EmployeePayrollData> dataByDate = service.employeePayrollDBService.getEmployeePayrollDataByDateRange(
                LocalDate.of(2022, 1, 1), LocalDate.now());
        dataByDate.forEach(System.out::println);

        System.out.println("---------- UC 6: Aggregation Analysis ----------");
        Map<String, Double> sumMale = service.employeePayrollDBService.getEmployeeDataByGenderAnalysis("SUM", "M");
        System.out.println("Sum of salary for Male: " + sumMale.get("M"));
        
        Map<String, Double> avgFemale = service.employeePayrollDBService.getEmployeeDataByGenderAnalysis("AVG", "F");
        System.out.println("Avg of salary for Female: " + avgFemale.get("F"));
    }

    // Step 1 - 6 testing connectivity
    private void testConnection() {
        String dbUrl = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String pass = "Aayush@01";

        try {
            // Check if MySQL JDBC driver class is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");

            // List the MySQL JDBC Drivers Registered
            System.out.println("Registered Drivers:");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                System.out.println(drivers.nextElement().getClass().getName());
            }

            // Get the SQL Connection
            try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {
                System.out.println("Connection Established");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load driver");
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to get connection", e);
        }
    }

    public List<EmployeePayrollData> readEmployeePayrollData() {
        return this.employeePayrollDBService.readData();
    }

    public void updateEmployeeSalary(String name, double salary) {
        int result = this.employeePayrollDBService.updateEmployeeData(name, salary);
        if (result == 0) return;
        List<EmployeePayrollData> employeePayrollDataList = this.readEmployeePayrollData();
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name, employeePayrollDataList);
        if (employeePayrollData != null) employeePayrollData.setSalary(salary);
    }
    
    public void updateEmployeeSalaryUsingPreparedStatement(String name, double salary) {
        int result = this.employeePayrollDBService.updateEmployeeDataUsingPreparedStatement(name, salary);
        if (result == 0) return;
        List<EmployeePayrollData> employeePayrollDataList = this.readEmployeePayrollData();
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name, employeePayrollDataList);
        if (employeePayrollData != null) employeePayrollData.setSalary(salary);
    }

    private EmployeePayrollData getEmployeePayrollData(String name, List<EmployeePayrollData> list) {
        return list.stream()
                .filter(employeeDataItem -> employeeDataItem.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = this.employeePayrollDBService.getEmployeePayrollData(name);
        List<EmployeePayrollData> employeePayrollDataFromMemory = this.readEmployeePayrollData();
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name, employeePayrollDataFromMemory));
    }
}
