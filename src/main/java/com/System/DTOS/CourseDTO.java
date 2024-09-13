package com.System.DTOS;

import java.time.LocalDate;

public class CourseDTO {
	@Override
	public String toString() {
		return "CourseDTO [courseName=" + courseName + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", description=" + description + ", price=" + price + "]";
	}
	private String courseName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
	private int price;
	public CourseDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate localDate) {
		this.endDate = localDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
