package com.example.employeeservice.controller;



import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.mapper.EmployeeXmlMapper;
import com.example.employeeservice.service.EmployeeService;
import com.example.employeeservice.validation.XmlValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final XmlValidator xmlValidator;
    private final EmployeeXmlMapper employeeXmlMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public EmployeeResponse createEmployee(
            @RequestBody String xmlRequest) {

        xmlValidator.validate(xmlRequest);

        EmployeeRequest request =
                employeeXmlMapper.toObject(xmlRequest);

        return employeeService.createEmployee(request);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @RequestBody String xmlRequest) {

        xmlValidator.validate(xmlRequest);

        EmployeeRequest request =
                employeeXmlMapper.toObject(xmlRequest);

        return employeeService.updateEmployee(id, request);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public List<EmployeeResponse> getAllEmployees() {

        return employeeService.getAllEmployees();
    }
}