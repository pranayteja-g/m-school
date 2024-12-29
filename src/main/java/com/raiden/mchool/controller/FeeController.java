package com.raiden.mchool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.dto.FeeDto;
import com.raiden.mchool.model.Fee;
import com.raiden.mchool.service.FeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class FeeController {
	private final FeeService feeService;

	public FeeController(FeeService feeService) {
		this.feeService = feeService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/f/create")
	public ResponseEntity<FeeDto> createFee(@Valid @RequestBody Fee fee) {
		return new ResponseEntity<>(feeService.createFee(fee), HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/f/all")
	public ResponseEntity<List<FeeDto>> getAllFees() {
		return new ResponseEntity<>(feeService.getAllFees(), HttpStatus.OK);
	}

//	@GetMapping("/f/student/{studentId}")
//	public ResponseEntity<List<Fee>> getStudentFees(@PathVariable Long studentId) {
//		List<Fee> fees = feeService.getStudentFees(studentId);
//		return fees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fees);
//	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/f/update/{id}")
	public ResponseEntity<FeeDto> updateFee(@PathVariable Long id, @RequestBody Fee fee) {
		return feeService.updateFee(id, fee).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/f/delete/{id}")
	public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
		feeService.deleteFee(id);
		return ResponseEntity.noContent().build();
	}
}
