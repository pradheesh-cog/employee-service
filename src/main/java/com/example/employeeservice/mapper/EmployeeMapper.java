package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {
        return Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .dateOfJoining(request.getDateOfJoining())
                .build();
    }

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()

                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .dateOfJoining(employee.getDateOfJoining())
                .build();
    }

    public void updateEmployee(Employee employee, EmployeeRequest request) {

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDateOfJoining(request.getDateOfJoining());
    }

}

