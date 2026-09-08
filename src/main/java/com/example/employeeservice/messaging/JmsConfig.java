package com.example.employeeservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class JmsConfig {


    @Bean
    public SimpleMessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }
}
