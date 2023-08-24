package com.thoughtworks.springbootemployee.exception;

//TODO: consider having ResponseStatus
public class EmployeeCreateException extends RuntimeException{

    public EmployeeCreateException (){
        super("Employee must be 18-65");
    }

}
