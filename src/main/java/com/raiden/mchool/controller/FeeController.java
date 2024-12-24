package com.raiden.mchool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.model.Fee;
import com.raiden.mchool.service.FeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/fees")
public class FeeController {
    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Fee> createFee(@Valid @RequestBody Fee fee) {
        return new ResponseEntity<>(feeService.createFee(fee), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Fee>> getStudentFees(@PathVariable Long studentId) {
        List<Fee> fees = feeService.getStudentFees(studentId);
        return fees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Fee> updateFee(@PathVariable Long id, @RequestBody Fee fee) {
        return feeService.updateFee(id, fee)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        feeService.deleteFee(id);
        return ResponseEntity.noContent().build();
    }
}
