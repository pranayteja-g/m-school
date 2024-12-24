package com.raiden.mchool.dto;

import java.util.List;

import com.raiden.mchool.model.ExamResult;
import com.raiden.mchool.model.Fee;
import com.raiden.mchool.model.Salary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private List<Fee> AllFees;
    private List<ExamResult> AllExamResults;
    private List<Salary> AllSalaries;
}
