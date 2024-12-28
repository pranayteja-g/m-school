package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raiden.mchool.dto.ExamResultDto;
import com.raiden.mchool.dto.StudentDto;
import com.raiden.mchool.model.ExamResult;
import com.raiden.mchool.repository.ExamResultRepository;
import com.raiden.mchool.repository.StudentRepository;

@Service
public class ExamResultService {
	private final ExamResultRepository examResultRepository;
	private final StudentRepository studentRepository;

	public ExamResultService(ExamResultRepository examResultRepository, StudentRepository studentRepository) {
		this.examResultRepository = examResultRepository;
		this.studentRepository = studentRepository;
	}

	// Create Exam Result and return ExamResultDto
	public ExamResultDto createExamResult(ExamResult examResult) {
		ExamResult savedResult = examResultRepository.save(examResult);
		return mapToDto(savedResult);
	}

	// Get All Exam Results with pagination, return ExamResultDto
	public Page<ExamResultDto> getAllExamResults(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ExamResult> examResults = examResultRepository.findAll(pageable);
		return examResults.map(this::mapToDto);
	}

	// Get Exam Results for a specific student, return ExamResultDto
	public Page<ExamResultDto> getStudentExamResults(Long studentId, Pageable pageable) {
		Page<ExamResult> examResults = examResultRepository.findByStudentId(studentId, pageable);
		return examResults.map(this::mapToDto);
	}

	// Update Exam Result and return updated ExamResultDto
	public Optional<ExamResultDto> updateExamResult(Long id, ExamResult updatedResult) {
		return examResultRepository.findById(id).map(result -> {
			result.setExamType(updatedResult.getExamType());
			result.setTotalMarks(updatedResult.getTotalMarks());
			result.setMarksObtained(updatedResult.getMarksObtained());
			ExamResult savedResult = examResultRepository.save(result);
			return mapToDto(savedResult);
		});
	}

	public void deleteExamResult(Long id) {
		examResultRepository.deleteById(id);
	}

	// Helper method to convert ExamResult to ExamResultDto
	private ExamResultDto mapToDto(ExamResult examResult) {
		ExamResultDto dto = new ExamResultDto();
		dto.setId(examResult.getId());
		dto.setExamType(examResult.getExamType());
		dto.setTotalMarks(examResult.getTotalMarks());
		dto.setMarksObtained(examResult.getMarksObtained());

		// Map the student details to StudentDto
		StudentDto studentDto = new StudentDto();
		studentDto.setId(examResult.getStudent().getId());
		studentDto.setName(examResult.getStudent().getName());
		studentDto.setStudentClass(examResult.getStudent().getStudentClass());
		studentDto.setSection(examResult.getStudent().getSection());
		studentDto.setRollNo(examResult.getStudent().getRollNo());

		dto.setStudentDto(studentDto);
		return dto;
	}
}
