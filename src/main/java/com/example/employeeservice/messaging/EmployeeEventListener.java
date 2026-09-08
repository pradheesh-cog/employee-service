package com.example.employeeservice.messaging;

import com.example.employeeservice.enums.AuditSource;
import com.example.employeeservice.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeEventListener {

    private final AuditLogService auditLogService;

    @JmsListener(destination = "${employee.queue.name}")
    public void receive(EmployeeEvent event) {

        auditLogService.log(
                event.getEmployeeId(),
                event.getAction(),
                AuditSource.JMS,
                null
        );
    }
}