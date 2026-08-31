package com.example.employeeservice.service;

import com.example.employeeservice.enums.AuditAction;
import com.example.employeeservice.enums.AuditSource;

public interface AuditLogService {

    void log(
            Long employeeId,
            AuditAction action,
            AuditSource source,
            String messageId
    );
}