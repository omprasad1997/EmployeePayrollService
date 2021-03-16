package com.javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class EmployeePayrollServiceTest {
     @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries(){
          EmployeePayrollData[] arrayOFEmp = {
                 new EmployeePayrollData(1,"Jeff Bezos",100000.0),
                 new EmployeePayrollData(2,"Bill Gates",200000.0),
                 new EmployeePayrollData(3,"Mark Zuckerberg",300000.0)
         };
         EmployeePayrollService employeePayrollService;
         employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOFEmp));
         employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
         employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
         long entries =employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
         Assertions.assertEquals(3,entries);
     }

     @Test
    public void givenFileOnReadingFromFileShouldMatchEmployee(){
         EmployeePayrollService employeePayrollService = new EmployeePayrollService();
         long entries = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
         Assertions.assertEquals(3,entries);
     }

     @Test
     public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
         EmployeePayrollService employeePayrollService = new EmployeePayrollService();
         List<EmployeePayrollData>  employeePayrollData = employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
         Assertions.assertEquals(3,employeePayrollData.size());
     }

    @Test
   public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData>  employeePayrollData = employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa",3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assertions.assertTrue(result);
     }
}
