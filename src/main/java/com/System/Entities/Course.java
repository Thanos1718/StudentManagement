package com.System.Entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity

public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private int price;
	
	
	private String courseDescription;
	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	
	
	@OneToMany(mappedBy="course",fetch=FetchType.LAZY)
	@JsonManagedReference("courseregistration")
	Set<CourseRegistration> courseRegistration=new HashSet<>();
	public Set<CourseRegistration> getCourseRegistration() {
		return courseRegistration;
	}

	public void setCourseRegistration(Set<CourseRegistration> courseRegistration) {
		this.courseRegistration = courseRegistration;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	@NotBlank
	@Column(unique=true)
	private String courseTitle;
	
	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}


	
	@Override
	public String toString() {
		return "Course [id=" + id + ", courseTitle=" + courseTitle + ", price=" + price + ", startDate=" + startDate
				+ ", endDate=" + endDate + "desc"+courseDescription+"]";
	}

	

	public Course() {
		// TODO Auto-generated constructor stub
	}

}
