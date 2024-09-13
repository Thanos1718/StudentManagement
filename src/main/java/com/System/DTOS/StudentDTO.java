package com.System.DTOS;

import java.util.List;

import com.System.Entities.Course;

public class StudentDTO {
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	//private List<RegisteredCourseDTO> courses;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public List<RegisteredCourseDTO> getCourses() {
//		return courses;
//	}
//
//	public void setCourses(List<RegisteredCourseDTO> coursedtos) {
//		this.courses = coursedtos;
//	}

	public StudentDTO() {
		// TODO Auto-generated constructor stub
	}
}
