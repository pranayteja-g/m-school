package com.raiden.mchool.controller.studentcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.dto.ExamResultDto;
import com.raiden.mchool.dto.StudentDto;
import com.raiden.mchool.model.ExamResult;
import com.raiden.mchool.model.Fee;
import com.raiden.mchool.model.Student;
import com.raiden.mchool.service.ExamResultService;
import com.raiden.mchool.service.FeeService;
import com.raiden.mchool.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentAccessController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private ExamResultService examResultService;

	@Autowired
	private FeeService feeService;

	@GetMapping("/id/{id}") // example: /student/id/1
	public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
		return studentService.getStudentById(id);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<StudentDto>> getStudentByName(@PathVariable String name) {
		List<StudentDto> students = studentService.getStudentsByName(name);
		if (students.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}

	@GetMapping("/student-id/{studentId}/exam-results")
	public ResponseEntity<Page<ExamResultDto>> getStudentExamResults(@PathVariable Long studentId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Page<ExamResultDto> results = examResultService.getStudentExamResults(studentId, PageRequest.of(page, size));
		return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
	}

	@GetMapping("/fees/{studentId}") // /student/fees/1
	public ResponseEntity<List<Fee>> getStudentFees(@PathVariable Long studentId) {
		List<Fee> fees = feeService.getStudentFees(studentId);
		return fees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fees);
	}

}
