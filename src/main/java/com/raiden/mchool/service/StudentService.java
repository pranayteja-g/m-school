package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.raiden.mchool.custom_exceptions.UsernameAlreadyTakenException;
import com.raiden.mchool.dto.StudentDto;
import com.raiden.mchool.model.Student;
import com.raiden.mchool.model.User;
import com.raiden.mchool.repository.StudentRepository;
import com.raiden.mchool.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

	private StudentRepository studentRepository;
	private UserRepository userRepository;
	private UserService userService;

	public StudentService(StudentRepository studentRepository, UserRepository userRepository, UserService userService) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public ResponseEntity<StudentDto> createStudent(Student student) {
		if (userRepository.existsByUsername(student.getUser().getUsername())) {
			throw new UsernameAlreadyTakenException("username already taken. try again");
		}

		if (studentRepository.existsByRollNo(student.getRollNo())) {
			throw new IllegalArgumentException("Roll number already exists. Please choose a unique roll number.");
		}

		// create and save the user through UserService
		User user = student.getUser();
		userService.createUser(user);
		Student savedStudent = studentRepository.save(student);

		StudentDto studentDto = new StudentDto();
		studentDto.setId(savedStudent.getId());
		studentDto.setName(savedStudent.getName());
		studentDto.setStudentClass(savedStudent.getStudentClass());
		studentDto.setSection(savedStudent.getSection());
		studentDto.setRollNo(savedStudent.getRollNo());

		return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
	}

	// Method to get all students
	public List<StudentDto> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		return students.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// Method to get a student by ID
	public ResponseEntity<StudentDto> getStudentById(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		return student.map(s -> new ResponseEntity<>(convertToDto(s), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Method to get students by name
	public List<StudentDto> getStudentsByName(String name) {
		List<Student> students = studentRepository.findByName(name);
		return students.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// Method to get student by username
	public ResponseEntity<StudentDto> getStudentByUsername(String username) {
		Optional<Student> student = studentRepository.findStudentByUsername(username);
		return student.map(s -> new ResponseEntity<>(convertToDto(s), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Method to update student
	public Optional<StudentDto> updateStudent(Long id, Student updatedStudent) {
		return studentRepository.findById(id).map(studentToUpdate -> {
			studentToUpdate.setName(updatedStudent.getName());
			studentToUpdate.setRollNo(updatedStudent.getRollNo());
			studentToUpdate.setStudentClass(updatedStudent.getStudentClass());
			studentToUpdate.setSection(updatedStudent.getSection());

			Student savedStudent = studentRepository.save(studentToUpdate);
			return convertToDto(savedStudent);
		});
	}

	public void deleteStudent(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
		studentRepository.delete(student);
	}

	// Helper method to convert Student entity to StudentDto
	private StudentDto convertToDto(Student student) {
		StudentDto studentDto = new StudentDto();
		studentDto.setId(student.getId());
		studentDto.setName(student.getName());
		studentDto.setStudentClass(student.getStudentClass());
		studentDto.setSection(student.getSection());
		studentDto.setRollNo(student.getRollNo());
		return studentDto;
	}

}
