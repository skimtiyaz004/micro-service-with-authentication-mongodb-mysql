package com.microservice.employeeserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.employeeserver.client.DepartmentClient;
import com.microservice.employeeserver.model.Department;
import com.microservice.employeeserver.model.Employee;
import com.microservice.employeeserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentClient departmentClient;
    @Autowired
    private ObjectMapper objectMapper;
    public void addEmployee(Employee req) {
        ResponseEntity<?> res=departmentClient.getDepartment(Long.valueOf(req.getDepartmentId()));
        System.out.println("res===>"+res);
        Department department=new Department();
        if (res.getStatusCode() == HttpStatus.OK) {

            Object body =res.getBody();
            department =objectMapper.convertValue(body, Department.class);;

        }
        Employee employee=new Employee();
        employee.setName(req.getName());
        employee.setDepartmentId(department.getId());
        employeeRepository.save(employee);


    }
}
