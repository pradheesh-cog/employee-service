package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.enums.AuditAction;
import com.example.employeeservice.enums.Department;
import com.example.employeeservice.exception.DuplicateEmployeeException;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.messaging.EmployeeEventProducer;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private AuditLogService auditLogService;

    @Mock
    private EmployeeEventProducer producer;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequest request;
    private EmployeeResponse response;

    @BeforeEach
    void setUp() {

        request = new EmployeeRequest();
        request.setName("John");
        request.setEmail("john@test.com");
        request.setDepartment(Department.IT);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setEmail("john@test.com");
        employee.setDepartment(Department.IT);

        response = new EmployeeResponse();
        response.setId(1L);
        response.setName("John");
        response.setEmail("john@test.com");
        response.setDepartment(Department.IT);
    }

    @Test
    void createEmployee_ShouldCreateSuccessfully() {

        when(employeeRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(employeeMapper.toEntity(request))
                .thenReturn(employee);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result =
                employeeService.createEmployee(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(employeeRepository).save(employee);

        verify(auditLogService).log(
                eq(1L),
                eq(AuditAction.CREATE),
                any(),
                isNull()
        );

        verify(producer).publish(any());
    }

    @Test
    void createEmployee_ShouldThrowException_WhenEmailExists() {

        when(employeeRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(
                DuplicateEmployeeException.class,
                () -> employeeService.createEmployee(request)
        );

        verify(employeeRepository, never()).save(any());
        verify(producer, never()).publish(any());
    }

    @Test
    void updateEmployee_ShouldUpdateSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result =
                employeeService.updateEmployee(1L, request);

        assertNotNull(result);

        verify(employeeMapper)
                .updateEmployee(employee, request);

        verify(employeeRepository)
                .save(employee);

        verify(auditLogService).log(
                eq(1L),
                eq(AuditAction.UPDATE),
                any(),
                isNull()
        );

        verify(producer).publish(any());
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.updateEmployee(1L, request)
        );
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result =
                employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );
    }

    @Test
    void getAllEmployees_ShouldReturnEmployees() {

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        List<EmployeeResponse> result =
                employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());

        verify(employeeRepository).findAll();
    }

    @Test
    void deleteEmployee_ShouldDeleteSuccessfully() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);

        verify(auditLogService).log(
                eq(1L),
                eq(AuditAction.DELETE),
                any(),
                isNull()
        );

        verify(producer).publish(any());
    }

    @Test
    void deleteEmployee_ShouldThrowException_WhenNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(1L)
        );

        verify(employeeRepository, never())
                .delete(any());
    }
}