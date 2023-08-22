package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> listAll(){
        return employeeRepository.listAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id){
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender){
        return employeeRepository.findByGender(gender);
    }

    @PostMapping("/addEmployee")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee createEmployee (@RequestBody Employee employee){
        return employeeRepository.saveEmployee(employee);
    }


    @GetMapping(params = {"pageNumber","pageSize"})
    public List<Employee>listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize){
        return employeeRepository.listByPage(pageNumber,pageSize);
    }

    @PutMapping("/updateEmployees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id);

        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setEmployeeAge(updatedEmployee.getEmployeeAge());
        existingEmployee.setEmployeeGender(updatedEmployee.getEmployeeGender());
        existingEmployee.setEmployeeSalary(updatedEmployee.getEmployeeSalary());
        existingEmployee.setCompanyId(updatedEmployee.getCompanyId());

        return existingEmployee;
    }

    @PutMapping("/deleteEmployees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
         employeeRepository.deleteEmployee(id);
         return "Employee " + id + " has been deleted.";
    }

}
