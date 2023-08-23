package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyApiTests {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanAll();
    }

    @Test
    void should_return_all_companies_when_perform_get_companies() throws Exception {
        // Given
        Company company = companyRepository.saveCompany(new Company(1L,"OOCL"));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(company.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(company.getCompanyName()));
    }

    @Test
    void should_return_created_company_when_perform_post_company_given_new_company() throws Exception {
        // Given
        Company newCompany = new Company(1L, "OOCL");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/companies/addCompany")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(notNullValue()))
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()));
    }

    @Test
    void should_return_company_when_perform_get_company_given_company_id() throws Exception {
        // Given
        Company newCompany = new Company(1L,"OOCL");
        new Company(2L,"COSCO");
        Company savedCompany = companyRepository.saveCompany(newCompany);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}", savedCompany.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(savedCompany.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(savedCompany.getCompanyName()));
    }

}
