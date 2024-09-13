package com.System.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.System.DTOS.CourseDTO;
import com.System.DTOS.RegisteredCourseDTO;
import com.System.DTOS.StudentDTO;
import com.System.DTOS.UserDTO;
import com.System.Entities.Student;
import com.System.Entities.User;
import com.System.Enum.UserRole;
import com.System.Service.StudentImpl;

import lombok.extern.slf4j.XSlf4j;

@RestController
@CrossOrigin(origins="*")
@XSlf4j
@RequestMapping("/student/")
public class StudentController {
	
	@Autowired 
	 StudentImpl service;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;


	
private static final Logger logger=LoggerFactory.getLogger(StudentController.class);
	
	
	
@PostMapping("/addStudent")
public ResponseEntity<Map<String,String>> addStudent(@RequestBody UserDTO dto) {
	
	Map<String,String> response=new HashMap();
	
	//System.out.println("student"+dto);
	Student student=new Student();
	student.setFirstName(dto.getFirstName());
	student.setLastName(dto.getLastName());
	student.setMobileNumber(dto.getMobile());
	student.setStudentemail(dto.getEmail());
	
	User user=new User();
	user.setPassword(passwordEncoder.encode(dto.getPassword()));
	user.setUseremail(dto.getEmail());
	user.setUsername(dto.getUsername());
	user.setUserRole(UserRole.STUDENT);

	Optional<Student> userifexist=Optional.ofNullable(service.addStudent(user,student));
	if(userifexist.isPresent() && !userifexist.isEmpty())
	{
		response.put("message", "Registered successfully!");
	  return  new ResponseEntity<Map<String,String>>(response,HttpStatus.CREATED);	
	}
	else
	{
		response.put("message", "Something went wrong!");
		  return  new ResponseEntity<Map<String,String>>(response,HttpStatus.CREATED);			}
}
	
	@PostMapping("/buyCourse")
	public ResponseEntity<Map<String,Object>> buyCourse(@RequestParam String email,@RequestParam String courseName,@RequestParam int state)
	{
		System.out.println("hitt"+email +" "+courseName+" +state"+state);
		logger.error("{} {} {}",email,courseName,state);
		String status= service.buyCourse(email,courseName,state);
		
		 Map<String, Object> response = new HashMap<>();
		    response.put("message", status);
		    response.put("status", HttpStatus.OK.value());
		    response.put("course",courseName);
		    
		return new  ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("getstudentCourses/{email}")
	public ResponseEntity<List<RegisteredCourseDTO>> getCourses(@PathVariable("email") String email)
	{
		List<RegisteredCourseDTO> courseDetails=service.getcourses(email);
			return new ResponseEntity<List<RegisteredCourseDTO>>(courseDetails,HttpStatus.OK);	
	}
	
	@GetMapping("getStudent/{email}")
	public ResponseEntity<StudentDTO> getDetails(@PathVariable("email") String email)
	{
		
		System.out.println("Get Student");
		 StudentDTO dto =service.getStudentData(email);
		 return new ResponseEntity<StudentDTO>(dto,HttpStatus.OK);
	}
	
	
	@PutMapping("/checkout")
	public ResponseEntity< Map<String, Object>> checkOut(@RequestParam("email")String email,@RequestParam("courseName")String courseName)
	{
		logger.error("hit checkout{} {}",email,courseName);
		service.updateCourseRegistration(email,courseName);
		 Map<String, Object> response = new HashMap<>();
		    response.put("message", "Purchased Successfully course ");
		    response.put("status", HttpStatus.OK.value());
		    response.put("course",courseName);
		return new ResponseEntity< Map<String, Object>>(response,HttpStatus.OK);
	}
		
	
	
	@GetMapping("/getAllcourses")
	public ResponseEntity<List<CourseDTO>> getCourses()
	{
		List<CourseDTO> courses=new ArrayList();
		courses.addAll(service.getAllCourses());
		return new ResponseEntity<List<CourseDTO>>(courses,HttpStatus.OK);
	}

	public StudentController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		
		
	}

}
;