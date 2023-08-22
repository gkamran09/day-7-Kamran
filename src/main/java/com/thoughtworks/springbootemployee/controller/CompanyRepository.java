package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    private static final long ID_INCREMENT = 1 ;
    private static final long START_ID_MINUS_ONE = 0L;

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

    public List<Company> listByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {
        company.setId(generateNextId());
        companies.add(company);
        return company;
    }

    private Long generateNextId() {
            return companies.stream()
                    .mapToLong(Company::getCompanyId)
                    .max()
                    .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }
}

