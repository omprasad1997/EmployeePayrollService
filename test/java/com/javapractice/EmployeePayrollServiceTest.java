package com.javapractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
//-------+
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData>  employeePayrollData =
                employeePayrollService.readEmployeePayrollDataForDateRange(EmployeePayrollService.IOService.DB_IO,startDate,endDate);
        System.out.println(employeePayrollData);
        Assertions.assertEquals(3,employeePayrollData.size());

    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnProperValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
        Map<String,Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(2000000.00) &&
                                averageSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.addEmployeeToPayroll("Mark",5000000.00,LocalDate.now(),"M");
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
        Assertions.assertTrue(result);
    }

    @Test
    public void given6Employees_WhenAddedToDB_ShouldMatchEmployeeEntries(){
         EmployeePayrollData[] arrayOfEmps={
                 new EmployeePayrollData(0,"Jeff Bezos","M",1000000.0,LocalDate.now()),
                 new EmployeePayrollData(0,"Bill Gate","M",2000000.0,LocalDate.now()),
                 new EmployeePayrollData(0,"Mark Z","M",3000000.0,LocalDate.now()),
                 new EmployeePayrollData(0,"Sundar","M",4000000.0,LocalDate.now()),
                 new EmployeePayrollData(0,"Mukesh","M",5000000.0,LocalDate.now()),
                 new EmployeePayrollData(0,"Anil","M",6000000.0,LocalDate.now())
         };
         EmployeePayrollService  employeePayrollService = new EmployeePayrollService();
         employeePayrollService.readEmployeePayrollDataFromDB(EmployeePayrollService.IOService.DB_IO);
         Instant start = Instant.now();
         employeePayrollService.addEmployeesToPayroll(Arrays.asList(arrayOfEmps));
         Instant end = Instant.now();
         System.out.println("Duration without thread : "+Duration.between(start,end));
         Instant threadStart = Instant.now();
         employeePayrollService.addEmployeesToPayrollWithThread(Arrays.asList(arrayOfEmps));
         Instant threadEnd = Instant.now();
         System.out.println("Duration with thread : "+Duration.between(threadStart,threadEnd));
         Assertions.assertEquals(15,employeePayrollService.countEntries(EmployeePayrollService.IOService.DB_IO));
    }
}
