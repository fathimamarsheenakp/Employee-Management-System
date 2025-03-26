package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	EmployeeRepository employeeRepository;
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}
	
	public Employee getEmployee(int id) {
	    return employeeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Employee with ID " + id + " not found"));
	}
	
	public Employee addEmployee(Employee emp) {
		return employeeRepository.save(emp);
	}
	
	public Employee updateEmployee(Employee emp) {
		return employeeRepository.save(emp);
	}
	
	public boolean deleteEmployee(int id) {
		Employee emp = employeeRepository.findById(id).get();
		
		if (emp != null) {
			employeeRepository.delete(emp);
			return true;
		}
		return false;
	}
}
