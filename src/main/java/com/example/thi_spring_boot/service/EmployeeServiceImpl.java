package com.example.thi_spring_boot.service;



import com.example.thi_spring_boot.entity.Employee;
import com.example.thi_spring_boot.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee create(Employee employee) throws Exception {
        // validate đơn giản
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new Exception("Name is required");
        }
        if (employee.getAge() == null || employee.getAge() < 0) {
            throw new Exception("Age must be >= 0");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new Exception("Salary must be >= 0");
        }

        // check trùng tên
        if (employeeRepository.findByName(employee.getName().trim()).isPresent()) {
            throw new Exception("A user with name " + employee.getName() + " already exist.");
        }

        employee.setName(employee.getName().trim());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee employee) throws Exception {
        Employee exist = employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new Exception("Name is required");
        }
        if (employee.getAge() == null || employee.getAge() < 0) {
            throw new Exception("Age must be >= 0");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new Exception("Salary must be >= 0");
        }

        // check trùng tên với thằng khác
        employeeRepository.findByName(employee.getName().trim())
                .filter(e -> !e.getId().equals(id))
                .ifPresent(e -> {
                    throw new RuntimeException("A user with name " + employee.getName() + " already exist.");
                });

        exist.setName(employee.getName().trim());
        exist.setAge(employee.getAge());
        exist.setSalary(employee.getSalary());

        return employeeRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
