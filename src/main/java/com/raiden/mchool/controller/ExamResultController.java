package com.raiden.mchool.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.dto.ExamResultDto;
import com.raiden.mchool.model.ExamResult;
import com.raiden.mchool.service.ExamResultService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/results")
public class ExamResultController {
	private final ExamResultService examResultService;

	public ExamResultController(ExamResultService examResultService) {
		this.examResultService = examResultService;
	}

	// Endpoint to create a new ExamResult
	@PostMapping("/create")
	public ResponseEntity<ExamResultDto> createExamResult(@Valid @RequestBody ExamResult examResult) {
		ExamResultDto resultDto = examResultService.createExamResult(examResult);
		return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
	}

	// Endpoint to get all exam results with pagination
	@GetMapping("/getall")
	public ResponseEntity<Page<ExamResultDto>> getExamResults(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		Page<ExamResultDto> results = examResultService.getAllExamResults(page, size);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

//    // Endpoint to get exam results of a specific student by studentId with pagination
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<Page<ExamResultDto>> getStudentExamResults(@PathVariable Long studentId,
//            @RequestParam("page") int page, @RequestParam("size") int size) {
//        Page<ExamResultDto> results = examResultService.getStudentExamResults(studentId, page, size);
//        return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
//    }

	// Endpoint to update an existing exam result by id
	@PutMapping("/update/{id}")
	public ResponseEntity<ExamResultDto> updateExamResult(@PathVariable Long id, @RequestBody ExamResult examResult) {
		return examResultService.updateExamResult(id, examResult).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteExamResult(@PathVariable Long id) {
		examResultService.deleteExamResult(id);
		return ResponseEntity.noContent().build();
	}
}
