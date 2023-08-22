package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Employee createEmployee (@RequestBody Employee employee){
        return employeeRepository.saveEmployee(employee);
    }



}
