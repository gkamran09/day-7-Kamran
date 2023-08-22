package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Company A"));
        companies.add(new Company(2L, "Company B"));
    }
    public List<Company> listAll() {
        return companies;
    }

    public Company findCompanyById(Long id) {
            return companies.stream()
                    .filter(company -> company.getCompanyId().equals(id))
                    .findFirst()
                    .orElseThrow(CompanyNotFoundException::new);
    }

//    public List<Employee>findEmployeeByCompanyId(Long companyId){
//        return employees.stream()
//                .filter(employee -> employee.getCompanyId().equals(companyId))
//                .collect(Collectors.toList());
//    }
}
