package com.thoughtworks.springbootemployee.exception;

//TODO: consider having ResponseStatus
public class EmployeeUpdateException extends RuntimeException{
    public EmployeeUpdateException(){
        super("Employee is inactive");
    }
}
