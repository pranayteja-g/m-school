package com.raiden.mchool.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raiden.mchool.model.ExamResult;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    Page<ExamResult> findByStudentId(Long studentId, Pageable pageable);

    List<ExamResult> findByExamType(String examType);
}
