package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "/companies")
@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired EmployeeRepository employeeRepository;

    @GetMapping
    public List<Company> listAll() {
        return companyRepository.listAll();
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable Long id){
        return companyRepository.findCompanyById(id);
    }
    @GetMapping("/{id}/employees")
    public List<Employee>getEmployeeByCompanyId(@PathVariable Long id){
        return employeeRepository.findEmployeeByCompanyId(id);
    }

}
