package com.raiden.mchool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "fees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double totalAmount;

	@Column(nullable = false)
	private Double paidAmount;

	@Column(nullable = false)
	private Double dueAmount;

	@Column(nullable = false)
	private String feeType;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "student-fee")
	private Student student;
}
