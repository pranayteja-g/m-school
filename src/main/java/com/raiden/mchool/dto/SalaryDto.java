package com.raiden.mchool.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
	private Long id;
	private Double salaryAmount;
	private LocalDate createdDate;
	private EmployeeDTO employeeDto;
}