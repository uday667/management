package com.springboot.employeeManagement.exceptions;

public class EmployeeExistedException extends RuntimeException{
    public EmployeeExistedException() {
        super("Employee already existed in database");
    }
}
