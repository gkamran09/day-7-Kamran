package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();

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
}
