package com.thoughtworks.springbootemployee.controller;

import org.springframework.expression.spel.ast.Literal;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private static final List<Employee> employees = new ArrayList<>();
    private static final long ID_INCREMENT = 1 ;
    private static final long START_ID_MINUS_ONE = 0L;

    static{
        employees.add(new Employee(1L,"Gerard",30,"male",99999));
        employees.add(new Employee(2L,"Kamran",30,"female",99239));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        Long id = generateNextId();

        Employee employeeToBeCreated = new Employee(id, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(employeeToBeCreated);

        return employeeToBeCreated;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }
}