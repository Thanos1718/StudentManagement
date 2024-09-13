package com.System.DTOS;

import java.time.LocalDate;

public class RegisteredCourseDTO {
	
	private String courseTitle;
	private int price;
	private String status;
	private LocalDate registrationTime;
	private String desc;
	private LocalDate startDate;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(LocalDate registrationTime) {
		this.registrationTime = registrationTime;
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
	private LocalDate endDate;

	@Override
	public String toString() {
		return "CourseDTO [coursename=" + courseTitle + ", price=" + price + ", addedTocart=" + status + ", desc="
				+ desc + "]";
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String coursename) {
		this.courseTitle = coursename;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	public RegisteredCourseDTO() {
		// TODO Auto-generated constructor stub
	}

}
