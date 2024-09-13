package com.System.Repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.System.Entities.Course;
import com.System.Entities.CourseRegistration;
import com.System.Entities.Student;

public interface CourseRegistrationRepo extends JpaRepository<CourseRegistration,Long> {

	@Query("SELECT cr FROM CourseRegistration cr WHERE cr.student.studentId=:studentId")
	Set<CourseRegistration> getcourseIDS(@Param("studentId")Long id);
	
	Optional<CourseRegistration> findByStudentAndCourse(Student student, Course course);
	
}
