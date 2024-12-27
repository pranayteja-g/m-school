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

	@PostMapping("/create")
	public ResponseEntity<ExamResult> createExamResult(@Valid @RequestBody ExamResult examResult) {
		return new ResponseEntity<>(examResultService.createExamResult(examResult), HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<Page<ExamResult>> getExamResults(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		return new ResponseEntity<>(examResultService.getAllExamResults(page, size), HttpStatus.OK);
	}

//    @GetMapping("/er/student/{studentId}")
//    public ResponseEntity<List<ExamResult>> getStudentExamResults(@PathVariable Long studentId) {
//        List<ExamResult> results = examResultService.getStudentExamResults(studentId);
//        return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
//    }

	@PutMapping("/update/{id}")
	public ResponseEntity<ExamResult> updateExamResult(@PathVariable Long id, @RequestBody ExamResult examResult) {
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
