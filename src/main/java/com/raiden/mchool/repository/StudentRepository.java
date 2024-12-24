package com.raiden.mchool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raiden.mchool.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByName(String name);

	@Query("SELECT s FROM Student s WHERE s.user.username = :username")
	Optional<Student> findStudentByUsername(@Param("username") String username);

}
