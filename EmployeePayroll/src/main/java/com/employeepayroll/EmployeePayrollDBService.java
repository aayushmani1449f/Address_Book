package com.employeepayroll;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollDBService {

    private static EmployeePayrollDBService instance;
    private Connection connection;
    private PreparedStatement employeePayrollDataStatement;
    private PreparedStatement updateEmployeeSalaryStatement;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "Aayush@01";

    private EmployeePayrollDBService() {
    }

    public static EmployeePayrollDBService getInstance() {
        if (instance == null) {
            instance = new EmployeePayrollDBService();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection conn = this.getConnection();
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                employeePayrollList = this.getEmployeePayrollData(resultSet);
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to read employee payroll data", e);
        }
        return employeePayrollList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws SQLException {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double salary = resultSet.getDouble("salary");
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            char gender = resultSet.getString("gender").charAt(0);
            employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate, gender));
        }
        return employeePayrollList;
    }

    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    private int updateEmployeeDataUsingStatement(String name, double salary) {
        String sql = String.format("UPDATE employee_payroll SET salary = %.2f WHERE name = '%s'", salary, name);
        try {
            Connection conn = this.getConnection();
            try (Statement statement = conn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to update employee salary using statement", e);
        }
    }

    public int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
        try {
            Connection conn = this.getConnection();
            if (updateEmployeeSalaryStatement == null || updateEmployeeSalaryStatement.isClosed() || updateEmployeeSalaryStatement.getConnection().isClosed()) {
                updateEmployeeSalaryStatement = conn.prepareStatement("UPDATE employee_payroll SET salary = ? WHERE name = ?");
            }
            updateEmployeeSalaryStatement.setDouble(1, salary);
            updateEmployeeSalaryStatement.setString(2, name);
            return updateEmployeeSalaryStatement.executeUpdate();
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to update employee salary using prepared statement", e);
        }
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollList = null;
        try {
            Connection conn = this.getConnection();
            if (this.employeePayrollDataStatement == null || this.employeePayrollDataStatement.isClosed() || this.employeePayrollDataStatement.getConnection().isClosed()) {
                this.employeePayrollDataStatement = conn.prepareStatement("SELECT * FROM employee_payroll WHERE name = ?");
            }
            this.employeePayrollDataStatement.setString(1, name);
            try (ResultSet resultSet = this.employeePayrollDataStatement.executeQuery()) {
                employeePayrollList = this.getEmployeePayrollData(resultSet);
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to read employee payroll data for given name", e);
        }
        return employeePayrollList;
    }

    public List<EmployeePayrollData> getEmployeePayrollDataByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM employee_payroll WHERE start_date BETWEEN '%s' AND '%s'",
                Date.valueOf(startDate), Date.valueOf(endDate));
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection conn = this.getConnection();
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                employeePayrollList = this.getEmployeePayrollData(resultSet);
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to retrieve employee payroll data by date range", e);
        }
        return employeePayrollList;
    }

    public Map<String, Double> getEmployeeDataByGenderAnalysis(String operation, String gender) {
        String sql = String.format("SELECT %s(salary) FROM employee_payroll WHERE gender = '%s' GROUP BY gender", operation, gender);
        Map<String, Double> dataByGender = new HashMap<>();
        try {
            Connection conn = this.getConnection();
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    dataByGender.put(gender, resultSet.getDouble(1));
                }
            }
        } catch (SQLException e) {
            throw new PayrollDBException("Failed to perform analysis operation", e);
        }
        return dataByGender;
    }
}
