package com.System.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.System.DTOS.CourseDTO;
import com.System.Entities.Course;
import com.System.Service.CourseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin("*")
public class CourseController {
	@Autowired
	CourseImpl courseImpl;
	private static final Logger logger= LoggerFactory.getLogger(CourseController.class);

	@PostMapping("/addCourse")
	public ResponseEntity<String> addCourse(@RequestBody CourseDTO course)
	{
		
		logger.error("course hit{}",course);
		
		//System.out.println("course add hit"+course);
		
		 if(courseImpl.addCourse(course))
			return new ResponseEntity("Course Added!",HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong!");
	}
	
	@GetMapping("/getCourse/{coursename}")
	public ResponseEntity<CourseDTO> getCourse(@PathVariable("coursename") String title )
	{
		//System.out.println("titkle"+title);
		CourseDTO dto=courseImpl.getCourse(title);
			return new ResponseEntity<CourseDTO>(dto,HttpStatus.OK);
	}
	
	@GetMapping("/courses")
	public ResponseEntity<?> getAllCourses()
	{
		Set<CourseDTO>courses= courseImpl.getAllCourses();
		logger.info("courses info{}",courses);
			return ResponseEntity.status(HttpStatus.OK).body(courses);
	}
	
	@DeleteMapping("/deleteCourse/{title}")
	public ResponseEntity<?> deleteCourse(@PathVariable("title") String title)
	{
		Optional<CourseDTO> course=Optional.ofNullable(courseImpl.getCourse(title));
		if(course.isPresent() )
		{
			CourseDTO c= course.get();
			boolean deleted=courseImpl.deleteCourse(c);
			if(deleted)
			 return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error while deleting try again!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("  course not exist!");
	}
	
	public CourseController() {
		// TODO Auto-generated constructor stub
	}

}
