package com.example.thi_spring_boot.controller;


import com.example.thi_spring_boot.entity.Employee;
import com.example.thi_spring_boot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeApiController {

    private final EmployeeService employeeService;

    public EmployeeApiController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAll(
            @RequestParam(name = "keyword", required = false) String keyword) {

        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(employeeService.search(keyword));
        }
        return ResponseEntity.ok(employeeService.getAll());
    }

    // GET /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error("User not found"));
        }
        return ResponseEntity.ok(employee);
    }

    // POST /api/employees
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        try {
            Employee created = employeeService.create(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(error(e.getMessage()));
        }
    }

    // PUT /api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updated = employeeService.update(id, employee);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(e.getMessage()));
        }
    }

    // DELETE /api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // helper trả lỗi dạng JSON
    private Map<String, String> error(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("error", msg);
        return map;
    }
}

