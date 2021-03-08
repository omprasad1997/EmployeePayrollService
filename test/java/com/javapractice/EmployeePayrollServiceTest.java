package com.javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
         long entries =employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
         Assertions.assertEquals(3,entries);
     }
}
