package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.controller.EmployeeRepository;
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

    @Test
    void should_return_the_employee_when_perform_get_employee_given_the_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.saveEmployee(new Employee(1L,"Alice", 24, "Female", 9000,1L));
        employeeRepository.saveEmployee(new Employee(2L,"Bob", 24, "Male", 9000,1L));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$.employeeName").value(alice.getEmployeeName()))
                .andExpect(jsonPath("$.employeeAge").value(alice.getEmployeeAge()))
                .andExpect(jsonPath("$.employeeGender").value(alice.getEmployeeGender()))
                .andExpect(jsonPath("$.employeeSalary").value(alice.getEmployeeSalary()))
                .andExpect(jsonPath("$.companyId").value(alice.getCompanyId()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_employee_given_not_existing_employee_id() throws Exception {
        //given
        long notExistingEmployeeId = 99L;
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistingEmployeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_list_of_employee_by_given_gender_when_perform_get_employee_given_gender() throws Exception{
        //Given
        Employee alice = employeeRepository.saveEmployee(new Employee(1L,"Alice", 24, "Female", 9000,1L));
        Employee bob = employeeRepository.saveEmployee(new Employee(2L,"Bob", 24, "Male", 9000,1L));

        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/").param("gender","Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").value(alice.getEmployeeId()))
                .andExpect(jsonPath("$[0].employeeName").value(alice.getEmployeeName()))
                .andExpect(jsonPath("$[0].employeeAge").value(alice.getEmployeeAge()))
                .andExpect(jsonPath("$[0].employeeGender").value(alice.getEmployeeGender()))
                .andExpect(jsonPath("$[0].employeeSalary").value(alice.getEmployeeSalary()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
    }
    @Test
    void should_return_the_employee_when_perform_post_employee_given_new_employee_with_JSON_format() throws Exception {
        // Given
        Employee newEmployee = new Employee(1L,"Alice", 24, "Female", 9000, 1L);
        // When, Then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(notNullValue()))
                .andExpect(jsonPath("$.employeeName").value(newEmployee.getEmployeeName()))
                .andExpect(jsonPath("$.employeeAge").value(newEmployee.getEmployeeAge()))
                .andExpect(jsonPath("$.employeeGender").value(newEmployee.getEmployeeGender()))
                .andExpect(jsonPath("$.employeeSalary").value(newEmployee.getEmployeeSalary()))
                .andExpect(jsonPath("$.companyId").value(newEmployee.getCompanyId()));
    }

    @Test
    void should_return_updated_employee_when_perform_put_employee_given_existing_employee_id_and_updated_data() throws Exception {
        // Given
        Employee existingEmployee = employeeRepository.saveEmployee(new Employee(1L, "Alice", 24, "Female", 9000, 1L));
        Employee updatedEmployee = new Employee(existingEmployee.getEmployeeId(), "Juliet", 25, "Female", 9500, 1L);

        // When, Then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/updateEmployees/" + existingEmployee.getEmployeeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(existingEmployee.getEmployeeId()))
                .andExpect(jsonPath("$.employeeName").value(updatedEmployee.getEmployeeName()))
                .andExpect(jsonPath("$.employeeAge").value(updatedEmployee.getEmployeeAge()))
                .andExpect(jsonPath("$.employeeGender").value(updatedEmployee.getEmployeeGender()))
                .andExpect(jsonPath("$.employeeSalary").value(updatedEmployee.getEmployeeSalary()))
                .andExpect(jsonPath("$.companyId").value(updatedEmployee.getCompanyId()));
    }

    @Test
    void should_return_no_content_when_perform_delete_employee_given_existing_employee_id() throws Exception {
        //given
        Employee existingEmployee = employeeRepository.saveEmployee(new Employee(1L, "Alice", 24, "Female", 9000, 1L));

        //when
        //then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/deleteEmployees/" + existingEmployee.getEmployeeId()))
                .andExpect(status().isNoContent());
    }
}