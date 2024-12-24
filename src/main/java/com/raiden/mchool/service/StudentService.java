package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.raiden.mchool.custom_exceptions.UsernameAlreadyTakenException;
import com.raiden.mchool.model.Student;
import com.raiden.mchool.repository.StudentRepository;
import com.raiden.mchool.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

	private StudentRepository studentRepository;
	private UserRepository userRepository;

	public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	public ResponseEntity<Student> createStudent(Student student) {
		if (userRepository.existsByUsername(student.getUser().getUsername())) {
			throw new UsernameAlreadyTakenException("username already taken. try again");
		}
		return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
	}

	public ResponseEntity<Student> getStudentById(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		return student.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public List<Student> getStudentsByName(String name) {
		return studentRepository.findByName(name);
	}

	public ResponseEntity<Student> getStudentByUsername(String username) {
		Optional<Student> student = studentRepository.findStudentByUsername(username);
		return student.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public Optional<Student> updateStudent(Long id, Student updatedStudent) {
		return studentRepository.findById(id).map(studentToUpdate -> {
			studentToUpdate.setName(updatedStudent.getName());
			studentToUpdate.setRollNo(updatedStudent.getRollNo());
			studentToUpdate.setStudentClass(updatedStudent.getStudentClass());
			studentToUpdate.setSection(updatedStudent.getSection());

			return studentRepository.save(studentToUpdate);
		});
	}

	public void deleteStudent(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
		studentRepository.delete(student);
	}

}
