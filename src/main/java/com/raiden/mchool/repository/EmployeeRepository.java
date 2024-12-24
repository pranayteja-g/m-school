package com.raiden.mchool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raiden.mchool.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.user.username = :username")
    Optional<Employee> findEmployeeByUsername(@Param("username") String username);
}
