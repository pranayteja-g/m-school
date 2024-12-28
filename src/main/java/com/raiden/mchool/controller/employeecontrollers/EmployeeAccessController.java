package com.raiden.mchool.controller.employeecontrollers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.dto.SalaryDto;
import com.raiden.mchool.dto.StudentDto;
import com.raiden.mchool.model.Employee;
import com.raiden.mchool.model.Salary;
import com.raiden.mchool.model.Student;
import com.raiden.mchool.service.EmployeeService;
import com.raiden.mchool.service.SalaryService;
import com.raiden.mchool.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeAccessController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SalaryService salaryService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/id/{id}") //example: api/employee/id/1
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}
	}

	@GetMapping("/salary/{employeeId}")  //example: api/employee/salary/1
	public ResponseEntity<List<SalaryDto>> getEmployeeSalaries(@PathVariable Long employeeId) {
		List<SalaryDto> salaries = salaryService.getEmployeeSalaries(employeeId);
		return salaries.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(salaries);
	}
	
	@GetMapping("/salary/between") // api/employee/salary/between
	public ResponseEntity<List<SalaryDto>> getSalariesBetweenDates(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
		List<SalaryDto> salaries = salaryService.getSalariesBetweenDates(startDate, endDate);
		return salaries.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(salaries);
	}
		
	

}
