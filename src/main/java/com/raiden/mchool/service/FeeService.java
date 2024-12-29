package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raiden.mchool.dto.FeeDto;
import com.raiden.mchool.dto.StudentDto;
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

	public FeeDto createFee(Fee fee) {
//		if(fee.getId() != null && fee.getVersion() == null) {
//			throw new IllegalArgumentException("detatched entity must have a valid version value");
//		}
		fee.setDueAmount(fee.getTotalAmount() - fee.getPaidAmount());
		feeRepository.save(fee);
		return mapToDto(fee);
	}

	public List<FeeDto> getAllFees() {
		List<Fee> fees = feeRepository.findAll();
		return fees.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<FeeDto> getStudentFees(Long studentId) {
		List<Fee> fees = feeRepository.findByStudentId(studentId);
		return fees.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public Optional<FeeDto> updateFee(Long id, Fee updatedFee) {
		return feeRepository.findById(id).map(fee -> {
			fee.setTotalAmount(updatedFee.getTotalAmount());
			fee.setPaidAmount(updatedFee.getPaidAmount());
			fee.setDueAmount(updatedFee.getTotalAmount() - updatedFee.getPaidAmount());
			fee.setFeeType(updatedFee.getFeeType());
			Fee savedFee = feeRepository.save(fee);
			return mapToDto(savedFee);
		});
	}

	public void deleteFee(Long id) {
		feeRepository.deleteById(id);
	}

	public FeeDto mapToDto(Fee fee) {
		FeeDto feeDto = new FeeDto();
		feeDto.setId(fee.getId());
		feeDto.setTotalAmount(fee.getTotalAmount());
		feeDto.setPaidAmount(fee.getPaidAmount());
		feeDto.setFeeType(fee.getFeeType());

		StudentDto studentDto = new StudentDto();
		studentDto.setId(fee.getStudent().getId());
		studentDto.setName(fee.getStudent().getName());
		studentDto.setStudentClass(fee.getStudent().getStudentClass());
		studentDto.setRollNo(fee.getStudent().getRollNo());

		feeDto.setStudentDto(studentDto);

		return feeDto;
	}
}