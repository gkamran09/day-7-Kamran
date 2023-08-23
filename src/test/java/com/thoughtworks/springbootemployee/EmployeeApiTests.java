package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.controller.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_employees_when_perform_get_employees() throws Exception {
        // Given
        Employee alice = employeeRepository.saveEmployee(new Employee(1L,"Alice", 24, "Female", 9000,1L));

        // When, Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$[0].employeeName").value(alice.getEmployeeName()))
                .andExpect(jsonPath("$[0].employeeAge").value(alice.getEmployeeAge()))
                .andExpect(jsonPath("$[0].employeeGender").value(alice.getEmployeeGender()))
                .andExpect(jsonPath("$[0].employeeSalary").value(alice.getEmployeeSalary()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));

    }
}