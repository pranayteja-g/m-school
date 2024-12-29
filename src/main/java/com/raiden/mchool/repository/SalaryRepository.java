package com.raiden.mchool.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raiden.mchool.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployeeId(Long employeeId);

    List<Salary> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}