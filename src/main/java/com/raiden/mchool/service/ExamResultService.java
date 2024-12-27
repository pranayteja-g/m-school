package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raiden.mchool.model.ExamResult;
import com.raiden.mchool.repository.ExamResultRepository;
import com.raiden.mchool.repository.StudentRepository;

@Service
public class ExamResultService {
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;

    public ExamResultService(ExamResultRepository examResultRepository, StudentRepository studentRepository) {
        this.examResultRepository = examResultRepository;
        this.studentRepository = studentRepository;
    }

    public ExamResult createExamResult(ExamResult examResult) {
        return examResultRepository.save(examResult);
    }

    public Page<ExamResult> getAllExamResults(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
    	return examResultRepository.findAll(pageable);
    }

    public Page<ExamResult> getStudentExamResults(Long studentId, Pageable pageable) {
        return examResultRepository.findByStudentId(studentId, pageable);
    }

    public Optional<ExamResult> updateExamResult(Long id, ExamResult updatedResult) {
        return examResultRepository.findById(id)
                .map(result -> {
                    result.setExamType(updatedResult.getExamType());
                    result.setTotalMarks(updatedResult.getTotalMarks());
                    result.setMarksObtained(updatedResult.getMarksObtained());
                    return examResultRepository.save(result);
                });
    }

    public void deleteExamResult(Long id) {
        examResultRepository.deleteById(id);
    }
}
