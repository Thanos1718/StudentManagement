package com.System.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.System.DTOS.CourseDTO;
import com.System.Entities.Student;
import com.System.Service.StudentImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/")
public class AdminController {
	
	private static final Logger logger=LoggerFactory.getLogger(AdminController.class);
	@Autowired 
	StudentImpl service;
	@GetMapping("/students")
	public ResponseEntity<?> listStudents()
	{
		  List<Student> students=service.getStudents();
		  return ResponseEntity.status(HttpStatus.OK).body(students);
		  
		   
	}
	
	@GetMapping("/getAllcourses")
	public ResponseEntity<List<CourseDTO>> getCourses()
	{
		System.out.println("admin hit");
		List<CourseDTO> courses=new ArrayList();
		courses.addAll(service.getAllCourses());
		return new ResponseEntity<List<CourseDTO>>(courses,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteStudent/{email}")
	public ResponseEntity<String> deleteStudent(@PathVariable("email") String email )
	
	{
		logger.error("delete {}{}",email);
			  boolean deleted=service.deleteStudent(email);
			  if(deleted)
			  return ResponseEntity.status(HttpStatus.OK).body("deleted Successfully");
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong try again!");	  
	}
	
	@DeleteMapping("/cancelCourse")
	public ResponseEntity<String> cancelSubscription(@RequestParam("email") String email,@RequestParam("course") String course)
	{
		System.out.println("Cancedl course"+course+email);
		
		String name =service.deleteCourseRegistration(email, course);
		return new ResponseEntity<String>("Cancelled subscription for: " +name+" of "+course,HttpStatus.OK);
	}
	
	
	public AdminController() {
		// TODO Auto-generated constructor stub
	}

}
