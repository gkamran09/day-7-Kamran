package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = {"pageNumber","pageSize"})
    public List<Company>listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize){
        return companyRepository.listByPage(pageNumber,pageSize);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addCompany")
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.saveCompany(company);
    }

    @PutMapping("/updateCompanies/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id);

        existingCompany.setCompanyName(updatedCompany.getCompanyName());

        return existingCompany;
    }

}
