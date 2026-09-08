package com.example.employeeservice.dto;

import com.example.employeeservice.enums.Department;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeRequest {
    String name;
    String email;
    Department department;
    String dateOfJoining;

    public LocalDate getCreatedDateAsLocalDateTime() {
        return LocalDate.parse(dateOfJoining);
    }


}
