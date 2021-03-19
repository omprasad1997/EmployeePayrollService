package com.javapractice;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    public  String gender;
    public int id;
    public String name;
    public double salary;
    public LocalDate startDate;

    public EmployeePayrollData(Integer id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayrollData(int id, String name, double salary, LocalDate startDate) {
        this(id,name,salary);
        this.startDate = startDate;
    }

    public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate date) {
        this(id,name,salary,date);
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash( name,gender, salary, startDate);
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.salary, salary) == 0 &&
                Objects.equals(gender, that.gender) && Objects.equals(name, that.name)
                && Objects.equals(startDate, that.startDate);
    }
}
