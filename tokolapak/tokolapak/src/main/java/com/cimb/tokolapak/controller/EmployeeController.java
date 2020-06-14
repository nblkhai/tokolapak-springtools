package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@PostMapping
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	
	@GetMapping
	public Iterable<Employee> getEmployee() {
		return employeeRepo.findAll();
	}
	
	@DeleteMapping("/address/{id}")
	public void deleteEmployeeAddressById(@PathVariable int id) {
		Optional<EmployeeAddress> employeeAddress = employeeAddressRepo.findById(id);
		
		if (employeeAddress.get() == null)
			throw new RuntimeException("Employee address not found");
		
		employeeService.deleteEmployeeAddress(employeeAddress.get());
	}
	// WEEKENDTASK 
	
	@PostMapping("/{id}")
	public Optional<Employee> addEmployeeAddressById(@PathVariable int id, @RequestBody EmployeeAddress employeeAddress) {
	
		Optional<Employee> employee = employeeRepo.findById(id);
		
		employeeAddress.setEmployee(employee.get());
		employeeAddressRepo.save(employeeAddress);
		employeeAddress.getEmployee().setEmployeeAddress(employeeAddress);
		employeeRepo.save(employee.get());

		return employeeRepo.findById(id);
	}
	
}