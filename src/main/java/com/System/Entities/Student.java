package com.System.Entities;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


@Entity

public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long studentId;

	@NotBlank(message="Name must be not empty")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Name must contain only alphabetic characters and spaces")
	private String firstName;
	

	public Long getStudentId() {
		return studentId;
	}




	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getMobileNumber() {
		return mobileNumber;
	}




	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}




	public Set<CourseRegistration> getCourseRegistered() {
		return courseRegistered;
	}




	public void setCourseRegistered(Set<CourseRegistration> courseRegistered) {
		this.courseRegistered = courseRegistered;
	}




	public String getStudentemail() {
		return studentemail;
	}




	public void setStudentemail(String studentemail) {
		this.studentemail = studentemail;
	}




//	@Override
//	public String toString() {
//		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName
//				+ ", mobileNumber=" + mobileNumber + ", courseRegistered=" + courseRegistered + ", studentemail="
//				+ studentemail + "]";
//	}



	@NotBlank(message="Name must be not be empty")
	private String lastName;
	
		@NotBlank(message="Mobile no must be not be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
	private String mobileNumber;
	
   
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)  
	@JsonManagedReference("studentregistration")// reference to student of courseregistration
	private Set<CourseRegistration> courseRegistered=new HashSet<>();
	
	@Email(message="Please enter valid email")
	@NotBlank(message="Email must be not empty")
	@Column(name = "studentemail", nullable = false, unique = true)
	private String studentemail;
	



	public Student() {
		// TODO Auto-generated constructor stub
	}

}
