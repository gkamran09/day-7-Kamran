package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeUpdateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private  EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp(){
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_valid_age(){
        //given
        Employee employee = new Employee(null, "Alice", 24, "Female", 9000, 1L);
        Employee savedEmployee = new Employee(1L, "Alice", 24, "Female", 9000, 1L);
        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getEmployeeId(),employeeResponse.getEmployeeId());
        assertEquals("Alice", employeeResponse.getEmployeeName());
        assertEquals(24,employeeResponse.getEmployeeAge());
        assertEquals("Female",employeeResponse.getEmployeeGender());
        assertEquals(9000,employeeResponse.getEmployeeSalary());
        assertEquals(1,employeeResponse.getCompanyId());

    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_age_less_than_18(){
        //given
        Employee employee = new Employee(null,"Lucy",17,"Female",5999,1L);

        //when
        EmployeeCreateException employeeCreateException =  assertThrows(EmployeeCreateException.class,() ->{
            employeeService.create(employee);
        });
        assertEquals("Employee must be 18-65",employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_age_less_than_18_and_greater_than_65(){
        //given
        Employee employee = new Employee(null,"Lucy",67,"Female",5999,1L);

        //when
        EmployeeCreateException employeeCreateException =  assertThrows(EmployeeCreateException.class,() ->{
            employeeService.create(employee);
        });
        assertEquals("Employee must be 18-65",employeeCreateException.getMessage());
    }

    @Test
    void should_set_employee_active_status_to_true_by_default_when_create_employee() {
        // Given
        Employee employee = new Employee(null,"Lucy",35,"Female",5999,1L);
        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(employee);
        // When
        Employee createEmployee = employeeService.create(employee);

        // Then
        assertTrue(createEmployee.isActive());
    }

    @Test
    void should_set_false_active_when_delete_given_employee_service_and_employee_id() {
        // Given
        Employee employee = new Employee(1L, "Lucy", 35, "Female", 5999, 1L);
        employee.setActive(true);

        when(mockedEmployeeRepository.findEmployeeById(employee.getEmployeeId()))
                .thenReturn(employee);

        // When
        employeeService.delete(employee.getEmployeeId());

        // Then
        assertFalse(employee.isActive());
        assertEquals("Lucy", employee.getEmployeeName());
        assertEquals(35, employee.getEmployeeAge());
        assertEquals("Female", employee.getEmployeeGender());
        assertEquals(5999, employee.getEmployeeSalary());
        assertEquals(1L, employee.getCompanyId());
    }

    @Test
    void should_throw_exception_when_update_given_inactive_employee() {
        // Given
        Employee employee = new Employee(1L, "Lucy", 35, "Female", 5999, 1L);
        employee.setActive(false);


        Employee updatedEmployee = new Employee(1L, "Lucy", 35, "Female", 5999, 1L);
        updatedEmployee.setActive(false);

        when(mockedEmployeeRepository.findEmployeeById(employee.getEmployeeId()))
                .thenReturn(employee);

        when(mockedEmployeeRepository.update(updatedEmployee))
                .thenAnswer(invocation -> {
                    Employee updated = invocation.getArgument(0);
                    return updated;
                });

        // When
        EmployeeUpdateException exception = assertThrows(EmployeeUpdateException.class, () -> {
            employeeService.update(employee.getEmployeeId(), updatedEmployee);
        });

        // Then
        assertEquals("Employee is inactive", exception.getMessage());
    }


}
