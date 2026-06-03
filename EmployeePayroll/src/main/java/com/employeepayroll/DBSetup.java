package com.employeepayroll;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DBSetup {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String pass = "Aayush@01";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
                 Statement stmt = conn.createStatement()) {

                // Create database
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS payroll_service");
                System.out.println("Database 'payroll_service' created or already exists.");

                // Use database
                stmt.execute("USE payroll_service");

                // Create table
                String createTableSql = "CREATE TABLE IF NOT EXISTS employee_payroll (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(150) NOT NULL, " +
                        "salary DOUBLE NOT NULL, " +
                        "start_date DATE NOT NULL, " +
                        "gender CHAR(1) NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")";
                stmt.executeUpdate(createTableSql);
                System.out.println("Table 'employee_payroll' created or already exists.");

                // Clear existing data for fresh start
                stmt.executeUpdate("TRUNCATE TABLE employee_payroll");

                // Insert data
                stmt.executeUpdate("INSERT INTO employee_payroll (name, salary, start_date, gender) VALUES ('Terisa', 2000000.00, '2023-01-01', 'F')");
                stmt.executeUpdate("INSERT INTO employee_payroll (name, salary, start_date, gender) VALUES ('Bill', 3500000.00, '2022-05-15', 'M')");
                stmt.executeUpdate("INSERT INTO employee_payroll (name, salary, start_date, gender) VALUES ('Charlie', 4000000.00, '2021-11-20', 'M')");
                stmt.executeUpdate("INSERT INTO employee_payroll (name, salary, start_date, gender) VALUES ('Alice', 3000000.00, '2024-02-10', 'F')");

                System.out.println("Inserted initial test data into 'employee_payroll'.");

            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
