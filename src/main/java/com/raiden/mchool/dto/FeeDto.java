package com.raiden.mchool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDto {

	private Long id;
	private Double totalAmount;
	private Double paidAmount;
	private String feeType;
	private StudentDto studentDto;
}

/*
 *
 * { "totalAmount": 20000.0, "paidAmount": 3000.0, "feeType": "hostel",
 * "student": { "id": 2 } }
 */
