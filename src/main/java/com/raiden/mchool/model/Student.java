package com.raiden.mchool.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id")
	private User user;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String studentClass;

	@Column(nullable = false)
	private String section;

	@Column(nullable = false, unique = true)
	private String rollNo;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "student-exam-result")
	private List<ExamResult> examResults;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "student-fee")
	private List<Fee> fees;
}
