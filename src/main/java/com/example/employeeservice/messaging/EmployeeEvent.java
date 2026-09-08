package com.example.employeeservice.messaging;

import com.example.employeeservice.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent implements Serializable {

    private Long employeeId;

    private AuditAction action;

    private LocalDateTime timestamp;
}