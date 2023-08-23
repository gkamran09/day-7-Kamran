package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeUpdateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public  EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee){
        if (employee.hasInvalidAge()){
            throw new EmployeeCreateException();
        }
        employee.setActive(true);
        return employeeRepository.saveEmployee(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findEmployeeById(id);
        matchedEmployee.setActive(false);
        employeeRepository.update(matchedEmployee);
    }

    public Employee update(Long employeeId, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findEmployeeById(employeeId);

        if (!existingEmployee.isActive()) {
            throw new EmployeeUpdateException();
        }
        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setEmployeeAge(updatedEmployee.getEmployeeAge());
        existingEmployee.setEmployeeGender(updatedEmployee.getEmployeeGender());
        existingEmployee.setEmployeeSalary(updatedEmployee.getEmployeeSalary());
        existingEmployee.setCompanyId(updatedEmployee.getCompanyId());
        existingEmployee.setActive(updatedEmployee.isActive());

        employeeRepository.update(existingEmployee);
        return existingEmployee;
    }

    public List<Employee> getEmployeesByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    public List<Employee> listAllEmployees() {
        return employeeRepository.listAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }
}
