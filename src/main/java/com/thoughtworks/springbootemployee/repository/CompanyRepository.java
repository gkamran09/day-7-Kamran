package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
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
        companies.add(new Company(3L, "Company C"));
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

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {
        company.setCompanyId(generateNextCompanyId());
        companies.add(company);
        return company;
    }

    private Long generateNextCompanyId() {
            return companies.stream()
                    .mapToLong(Company::getCompanyId)
                    .max()
                    .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }
    public void deleteCompany(Long id) {
        companies.removeIf(company -> company.getCompanyId().equals(id));
    }

    public void cleanAll() {
        companies.clear();
    }
}

