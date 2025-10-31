package com.example.thi_spring_boot.service;



import com.example.thi_spring_boot.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    List<Employee> search(String keyword);

    Employee getById(Long id);

    Employee create(Employee employee) throws Exception;

    Employee update(Long id, Employee employee) throws Exception;

    void delete(Long id);
}

