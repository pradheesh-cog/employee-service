package com.example.employeeservice.service.impl;

import com.example.employeeservice.entity.AuditLog;
import com.example.employeeservice.enums.AuditAction;
import com.example.employeeservice.enums.AuditSource;
import com.example.employeeservice.repository.AuditLogRepository;
import com.example.employeeservice.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(
            Long employeeId,
            AuditAction action,
            AuditSource source,
            String messageId) {

        AuditLog auditLog = AuditLog.builder()
                .employeeId(employeeId)
                .action(action)
                .source(source)
                .messageId(messageId)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }
}