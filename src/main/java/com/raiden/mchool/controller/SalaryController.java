package com.raiden.mchool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.model.Salary;
import com.raiden.mchool.service.SalaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class SalaryController {
	private final SalaryService salaryService;

	public SalaryController(SalaryService salaryService) {
		this.salaryService = salaryService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/sal/create")
	public ResponseEntity<Salary> createSalary(@Valid @RequestBody Salary salary) {
		return new ResponseEntity<>(salaryService.createSalary(salary), HttpStatus.CREATED);
	}

//	@GetMapping("/sal/employee/{employeeId}")
//	public ResponseEntity<List<Salary>> getEmployeeSalaries(@PathVariable Long employeeId) {
//		List<Salary> salaries = salaryService.getEmployeeSalaries(employeeId);
//		return salaries.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(salaries);
//	}
//
//	@GetMapping("/sal/between")
//	public ResponseEntity<List<Salary>> getSalariesBetweenDates(
//			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//		List<Salary> salaries = salaryService.getSalariesBetweenDates(startDate, endDate);
//		return salaries.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(salaries);
//	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/sal/update/{id}")
	public ResponseEntity<Salary> updateSalary(@PathVariable Long id, @RequestBody Salary salary) {
		return salaryService.updateSalary(id, salary).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/sal/delete/{id}")
	public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
		salaryService.deleteSalary(id);
		return ResponseEntity.noContent().build();
	}
}