package com.thoughtworks.springbootemployee.controller;

public class EmployeeNotFoundException extends RuntimeException{
    super("Employee not found");
}
