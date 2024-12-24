package com.raiden.mchool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.dto.DashboardResponse;
import com.raiden.mchool.service.ExamResultService;
import com.raiden.mchool.service.FeeService;
import com.raiden.mchool.service.SalaryService;

@RestController
@RequestMapping("/v1/api/dashboard")
public class DashboardController {

    @Autowired
    private FeeService feeService;

    @Autowired
    private ExamResultService examResultService;

    @Autowired
    private SalaryService salaryService;

    public ResponseEntity<DashboardResponse> getDashboardData() {
        DashboardResponse response = new DashboardResponse();
        response.setAllFees(feeService.getAllFees());
        response.setAllExamResults(examResultService.getAllExamResults());
        response.setAllSalaries(salaryService.getAllSalaries());
        return ResponseEntity.ok(response);
    }

}
