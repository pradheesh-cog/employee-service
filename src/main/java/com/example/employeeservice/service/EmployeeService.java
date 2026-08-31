package com.example.employeeservice.service;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    void deleteEmployee(Long id);
}