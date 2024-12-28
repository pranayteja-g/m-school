package com.raiden.mchool.dto;

import lombok.Data;

@Data
public class ExamResultDto {

	private Long id;
	private String examType;
	private Double totalMarks;
	private Double markObtained;
	private StudentDto studentDto;
}
