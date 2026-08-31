package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.exception.XmlParsingException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;

@Component
public class EmployeeXmlMapper {

    public EmployeeRequest toObject(String xml) {

        try {
            JAXBContext context = JAXBContext.newInstance(EmployeeRequest.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (EmployeeRequest) unmarshaller.unmarshal(
                    new StringReader(xml)
            );

        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse employee XML", e);
        }
    }

    public String toXml(EmployeeResponse response) {

        try {
            JAXBContext context = JAXBContext.newInstance(response.getClass());

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(
                    Marshaller.JAXB_FORMATTED_OUTPUT,
                    true
            );

            StringWriter writer = new StringWriter();

            marshaller.marshal(response, writer);

            return writer.toString();

        } catch (JAXBException e) {
            throw new XmlParsingException("Failed to convert object to XML", e);
        }
    }
}