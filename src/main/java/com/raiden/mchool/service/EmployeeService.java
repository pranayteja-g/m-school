package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.raiden.mchool.custom_exceptions.UsernameAlreadyTakenException;
import com.raiden.mchool.model.Employee;
import com.raiden.mchool.repository.EmployeeRepository;
import com.raiden.mchool.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private UserRepository userRepository;

	public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
	}

	public Employee createEmployee(Employee employee) {
		if (employee.getUser() == null) {
			throw new IllegalArgumentException("User details are missing for the employee.");
		}
		if (userRepository.existsByUsername(employee.getUser().getUsername())) {
			throw new UsernameAlreadyTakenException("username already taken. try again");
		}
		return employeeRepository.save(employee);
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	public List<Employee> getEmployeeByName(String name) {
		return employeeRepository.findByName(name);
	}

	public Employee getEmployeeByUsername(String username) {
		return employeeRepository.findEmployeeByUsername(username).orElse(null);
	}

	public Optional<Employee> updateEmployee(Long id, Employee updatedEmployee) {
		return employeeRepository.findById(id).map(employeeToUpdate -> {
			employeeToUpdate.setName(updatedEmployee.getName());
			employeeToUpdate.setSubject(updatedEmployee.getSubject());
			employeeToUpdate.setClassesTaught(updatedEmployee.getClassesTaught());

			return employeeRepository.save(employeeToUpdate);
		});
	}

	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
		employeeRepository.delete(employee);
	}
}