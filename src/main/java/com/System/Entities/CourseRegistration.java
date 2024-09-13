package com.System.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseRegistration {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private LocalDateTime registrationDate;
	
//	@Override
//	public String toString() {
//		return "CourseRegistration [id=" + id + ", registrationDate=" + registrationDate + ", isPurchased="
//				+ isPurchased + ", addedToCart=" + addedToCart + ", student=" + student + ", course=" + course + "]";
//	}



	private boolean isPurchased;
	
	private boolean addedToCart;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("studentregistration")
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference("courseregistration")
	@JoinColumn(name="course_id")
	private Course course;
	
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	@Column(name="course_name")
	private String courseName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CourseRegistration(Long id, LocalDateTime registrationDate, boolean isPurchased, boolean addedToCart,
			Student student, Course course) {
		super();
		this.id = id;
		this.registrationDate = registrationDate;
		this.isPurchased = isPurchased;
		this.addedToCart = addedToCart;
		this.student = student;
		this.course = course;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isPurchased() {
		return isPurchased;
	}

	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	public boolean isAddedToCart() {
		return addedToCart;
	}

	public void setAddedToCart(boolean addedToCart) {
		this.addedToCart = addedToCart;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	
	
	public CourseRegistration() {
		// TODO Auto-generated constructor stub
	}

}
