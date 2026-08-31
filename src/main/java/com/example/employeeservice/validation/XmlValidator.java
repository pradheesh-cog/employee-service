package com.example.employeeservice.validation;

import com.example.employeeservice.exception.XmlValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

@Component
public class XmlValidator {

    @Value("${xml.schema.path}")
    private final String xsdPath;

    public XmlValidator(
             String xsdPath) {
        this.xsdPath = xsdPath;
    }

    public void validate(String xml) {

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(
                    getClass().getClassLoader().getResource(xsdPath));

            Validator validator = schema.newValidator();

            validator.validate(
                    new StreamSource(new StringReader(xml)));

        } catch (SAXException | IOException e) {
            throw new XmlValidationException(
                    "Invalid XML: " + e.getMessage(), e);
        }
    }
}