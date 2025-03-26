package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin
public class EmployeeController {
	
	EmployeeService employeeService;
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/getAll")
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
	    Employee employee = employeeService.getEmployee(id);
	    return employee != null ? ResponseEntity.ok(employee) 
	                            : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }
	
	@PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok("Employee deleted successfully!");
        }
        return ResponseEntity.status(404).body("Employee not found or could not be deleted!");
    }
}
