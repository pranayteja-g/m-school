package com.raiden.mchool.controller.studentcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.model.Student;
import com.raiden.mchool.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/s")
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
		return studentService.createStudent(student);
	}

//	@GetMapping("/id/{id}")
//	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
//		return studentService.getStudentById(id);
//	}

//	@GetMapping("/name/{name}")
//	public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name) {
//		List<Student> students = studentService.getStudentsByName(name);
//		if (students.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		} else {
//			return ResponseEntity.ok(students);
//		}
//	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
		return studentService.updateStudent(id, updatedStudent).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}

}
