package com.raiden.mchool.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raiden.mchool.dto.SalaryDto;
import com.raiden.mchool.dto.EmployeeDTO;
import com.raiden.mchool.model.Salary;
import com.raiden.mchool.repository.EmployeeRepository;
import com.raiden.mchool.repository.SalaryRepository;

@Service
public class SalaryService {
	private final SalaryRepository salaryRepository;
	private final EmployeeRepository employeeRepository;

	public SalaryService(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
		this.salaryRepository = salaryRepository;
		this.employeeRepository = employeeRepository;
	}

	public Salary createSalary(Salary salary) {
		salary.setCreatedDate(LocalDate.now());
		return salaryRepository.save(salary);
	}

	public List<SalaryDto> getAllSalaries() {
		List<Salary> salaries = salaryRepository.findAll();
		return salaries.stream().map(this::convertToSalaryDTO).collect(Collectors.toList());
	}

	public List<SalaryDto> getEmployeeSalaries(Long employeeId) {
		List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
		return salaries.stream().map(this::convertToSalaryDTO).collect(Collectors.toList());
	}

	public List<SalaryDto> getSalariesBetweenDates(LocalDate startDate, LocalDate endDate) {
		List<Salary> salaries = salaryRepository.findByCreatedDateBetween(startDate, endDate);
		return salaries.stream().map(this::convertToSalaryDTO).collect(Collectors.toList());
	}

	public Optional<SalaryDto> updateSalary(Long id, Salary updatedSalary) {
		return salaryRepository.findById(id).map(salary -> {
			salary.setSalaryAmount(updatedSalary.getSalaryAmount());
			Salary savedSalary = salaryRepository.save(salary);
			return convertToSalaryDTO(savedSalary);
		});
	}

	public void deleteSalary(Long id) {
		salaryRepository.deleteById(id);
	}

	private SalaryDto convertToSalaryDTO(Salary salary) {
		EmployeeDTO employeeDTO = new EmployeeDTO(salary.getEmployee().getId(), salary.getEmployee().getName(),
				salary.getEmployee().getSubject());

		return new SalaryDto(salary.getId(), salary.getSalaryAmount(), salary.getCreatedDate(), employeeDTO);
	}
}