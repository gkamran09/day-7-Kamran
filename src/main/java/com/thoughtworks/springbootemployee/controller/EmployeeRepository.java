package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private static final List<Employee> employees = new ArrayList<>();
    private static final long ID_INCREMENT = 1 ;
    private static final long START_ID_MINUS_ONE = 0L;

    static{
        employees.add(new Employee(1L,"Gerard",30,"male",99999, 1L));
        employees.add(new Employee(2L,"Kamran",30,"female",99239, 1L));
        employees.add(new Employee(3L,"Aubs",30,"female",99239, 1L));
        employees.add(new Employee(4L,"Juliet",30,"female",99239, 2L));
        employees.add(new Employee(5L,"Linne",30,"female",99239, 2L));
        employees.add(new Employee(6L,"Ying",30,"male",99239, 2L));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        Long id = generateNextId();
        employee.setId(id);
        employees.add(employee);

        return employee;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getEmployeeId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void deleteEmployee(Long id) {
        employees.removeIf(employee -> employee.getEmployeeId().equals(id));
    }

    public List<Employee> findEmployeeByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }
    public void cleanAll(){
        employees.clear();
    }
}

