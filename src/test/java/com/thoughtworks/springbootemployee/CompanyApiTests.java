package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
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

    @Test
    void should_return_list_of_employees_in_company_when_perform_get_employees_by_company_id() throws Exception {
        // Given
        Company company = companyRepository.saveCompany(new Company(1L, "OOCL"));
        Employee employee1 = employeeRepository.findEmployeeById(1L);
        Employee employee2 = employeeRepository.findEmployeeById(2L);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/" + company.getCompanyId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].employeeId").value(employee1.getEmployeeId()))
                .andExpect(jsonPath("$[0].employeeName").value(employee1.getEmployeeName()))
                .andExpect(jsonPath("$[0].employeeAge").value(employee1.getEmployeeAge()))
                .andExpect(jsonPath("$[0].employeeGender").value(employee1.getEmployeeGender()))
                .andExpect(jsonPath("$[0].employeeSalary").value(employee1.getEmployeeSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee1.getCompanyId()))
                .andExpect(jsonPath("$[1].employeeId").value(employee2.getEmployeeId()))
                .andExpect(jsonPath("$[1].employeeName").value(employee2.getEmployeeName()))
                .andExpect(jsonPath("$[1].employeeAge").value(employee2.getEmployeeAge()))
                .andExpect(jsonPath("$[1].employeeGender").value(employee2.getEmployeeGender()))
                .andExpect(jsonPath("$[1].employeeSalary").value(employee2.getEmployeeSalary()))
                .andExpect(jsonPath("$[1].companyId").value(employee2.getCompanyId()));
    }

    @Test
    void should_update_company_name_when_perform_put_company_given_existing_company_id_and_updated_data() throws Exception {
        // Given
        Company existingCompany = companyRepository.saveCompany(new Company(null, "OOCL"));
        Company updatedCompany = new Company(existingCompany.getCompanyId(), "ThoughtWorks");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/updateCompanies/" + existingCompany.getCompanyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(existingCompany.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(updatedCompany.getCompanyName()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_company_given_not_existing_company_id() throws Exception {
        // Given
        long nonExistingCompanyId = 99L;

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/" + nonExistingCompanyId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_no_content_when_perform_delete_company_given_existing_company_id() throws Exception {
        //given
        Company existingCompany = companyRepository.saveCompany(new Company(null, "OOCL"));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/deleteEmployees/" + existingCompany.getCompanyId()))
                .andExpect(status().isNoContent());
    }

}
