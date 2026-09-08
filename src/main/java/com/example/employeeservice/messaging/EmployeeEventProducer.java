package com.example.employeeservice.messaging;

import com.example.employeeservice.messaging.EmployeeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeEventProducer {

    private final JmsTemplate jmsTemplate;

    @Value("${employee.queue.name}")
    private String queueName;

    public void publish(EmployeeEvent event) {

        jmsTemplate.convertAndSend(
                queueName,
                event
        );
    }
}