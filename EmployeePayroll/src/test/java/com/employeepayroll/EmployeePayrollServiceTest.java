package com.employeepayroll;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EmployeePayrollServiceTest {
    
    @Before
    public void setup() {
        // Just calling readData to ensure data is loaded if needed, or restoring DB state if necessary.
        // For now, DB setup handles initial data.
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assert.assertTrue(result);
    }
    
    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        employeePayrollService.updateEmployeeSalaryUsingPreparedStatement("Terisa", 3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assert.assertTrue(result);
    }
}
