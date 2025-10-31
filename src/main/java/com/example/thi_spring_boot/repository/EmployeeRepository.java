package com.example.thi_spring_boot.repository;



import com.example.thi_spring_boot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String name);

    List<Employee> findByNameContainingIgnoreCase(String keyword);
}


