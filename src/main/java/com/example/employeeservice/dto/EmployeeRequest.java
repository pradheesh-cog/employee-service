package com.example.employeeservice.dto;

import com.example.employeeservice.enums.Department;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeRequest {
    String name;
    String email;
    Department department;
    LocalDate dateOfJoining;

}
