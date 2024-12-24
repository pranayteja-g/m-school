package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raiden.mchool.model.Fee;
import com.raiden.mchool.repository.FeeRepository;
import com.raiden.mchool.repository.StudentRepository;

@Service
public class FeeService {
    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;

    public FeeService(FeeRepository feeRepository, StudentRepository studentRepository) {
        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
    }

    public Fee createFee(Fee fee) {
        fee.setDueAmount(fee.getTotalAmount() - fee.getPaidAmount());
        return feeRepository.save(fee);
    }

    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    public List<Fee> getStudentFees(Long studentId) {
        return feeRepository.findByStudentId(studentId);
    }

    public Optional<Fee> updateFee(Long id, Fee updatedFee) {
        return feeRepository.findById(id)
                .map(fee -> {
                    fee.setTotalAmount(updatedFee.getTotalAmount());
                    fee.setPaidAmount(updatedFee.getPaidAmount());
                    fee.setDueAmount(updatedFee.getTotalAmount() - updatedFee.getPaidAmount());
                    fee.setFeeType(updatedFee.getFeeType());
                    return feeRepository.save(fee);
                });
    }

    public void deleteFee(Long id) {
        feeRepository.deleteById(id);
    }
}