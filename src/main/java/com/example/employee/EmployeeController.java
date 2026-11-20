
package com.example.employee;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Map<String, Object>> employees =
            new ArrayList<>();

    public EmployeeController() {
        employees.add(Map.of(
                "id", 1,
                "name", "Rahul",
                "department", "DevOps"
        ));

        employees.add(Map.of(
                "id", 2,
                "name", "Priya",
                "department", "Development"
        ));
    }

    @GetMapping
    public List<Map<String, Object>> getEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getEmployee(@PathVariable int id) {
        return employees.stream()
                .filter(employee -> employee.get("id").equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found"
                        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> addEmployee(
            @RequestBody Map<String, Object> employee) {

        employees.add(employee);
        return employee;
    }
}

