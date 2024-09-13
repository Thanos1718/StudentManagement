package com.System.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.System.Entities.Course;

@Repository
public interface CourseRepository extends JpaRepository <Course,Long> {
	public Course findBycourseTitle(String title);
	
	
	
}
