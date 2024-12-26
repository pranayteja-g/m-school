package com.raiden.mchool.controller.studentcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		return studentService.getStudentById(id);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name) {
		List<Student> students = studentService.getStudentsByName(name);
		if (students.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}

	@GetMapping("/results/{studentId}") // /student/results/1
	public ResponseEntity<List<ExamResult>> getStudentExamResults(@PathVariable Long studentId) {
		List<ExamResult> results = examResultService.getStudentExamResults(studentId);
		return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
	}

	@GetMapping("/fees/{studentId}") // /student/fees/1
	public ResponseEntity<List<Fee>> getStudentFees(@PathVariable Long studentId) {
		List<Fee> fees = feeService.getStudentFees(studentId);
		return fees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fees);
	}

}
