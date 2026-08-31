package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.enums.AuditAction;
import com.example.employeeservice.enums.AuditSource;
import com.example.employeeservice.exception.DuplicateEmployeeException;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.AuditLogService;
import com.example.employeeservice.service.EmployeeService;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AuditLogService auditLogService;

    @Override
    public EmployeeResponse createEmployee(
            EmployeeRequest request) {

        if(employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmployeeException(request.getEmail());
        }

        Employee employee =
                employeeMapper.toEntity(request);

        employee = employeeRepository.save(employee);

        auditLogService.log(
                employee.getId(),
                AuditAction.CREATE,
                AuditSource.REST,
                null
        );

        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeMapper.updateEmployee(employee, request);

        employee = employeeRepository.save(employee);

        auditLogService.log(
                employee.getId(),
                AuditAction.UPDATE,
                AuditSource.REST,
                null
        );

        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);

        auditLogService.log(
                id,
                AuditAction.DELETE,
                AuditSource.REST,
                null
        );
    }


}

