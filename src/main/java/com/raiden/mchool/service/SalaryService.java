package com.raiden.mchool.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
        salary.setCreatedDate(LocalDateTime.now());
        return salaryRepository.save(salary);
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public List<Salary> getEmployeeSalaries(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    public List<Salary> getSalariesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return salaryRepository.findByCreatedDateBetween(startDate, endDate);
    }

    public Optional<Salary> updateSalary(Long id, Salary updatedSalary) {
        return salaryRepository.findById(id)
                .map(salary -> {
                    salary.setSalaryAmount(updatedSalary.getSalaryAmount());
                    return salaryRepository.save(salary);
                });
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}