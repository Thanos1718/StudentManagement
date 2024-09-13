package com.System.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.System.DTOS.UserDTO;
import com.System.Entities.AuthenticationResponse;
import com.System.Entities.Student;
import com.System.Entities.User;
import com.System.Enum.UserRole;
import com.System.Exceptions.RecordNotFoundException;
import com.System.Repository.UserRepository;
import com.System.Service.StudentImpl;
import com.System.Service.UserImpl;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")

public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	UserImpl user;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);

	@GetMapping("/")
	public String get() {
		return "Welcome to Home Page!";
	}
	
	@Autowired 
	 StudentImpl service;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> addStudent(@RequestBody UserDTO dto) {
	
		System.out.println("register api hit"+dto);
		Map<String,String> response=new HashMap();
		
		//System.out.println("student"+dto);
		Student student=new Student();
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setMobileNumber(dto.getMobile());
		student.setStudentemail(dto.getEmail());
		
		User user=new User();
		user.setPassword(encoder.encode(dto.getPassword()));
		user.setUseremail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setUserRole(UserRole.STUDENT);
	
		Optional<Student> userifexist=Optional.ofNullable(service.addStudent(user,student));
		if(userifexist.isPresent() && !userifexist.isEmpty())
		{
			response.put("message", "Registered Successfully!");
		  return  new ResponseEntity<Map<String,String>>(response,HttpStatus.CREATED);	
		}
		else
		{
			response.put("message", "Something went wrong!");
			  return  new ResponseEntity<Map<String,String>>(response,HttpStatus.CREATED);			}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin( @RequestBody UserDTO receiveduser)
	{
		logger.error("received for login {}",receiveduser);
		try {	
		AuthenticationResponse response=user.authenticate(receiveduser);
		
		User data=user.getUser(receiveduser.getEmail());
		
		return ResponseEntity.ok(response);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/getData/{email}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("email") String email)
	{
		logger.error("Get user ",email);
		UserDTO dto=new UserDTO();
		User user1=user.getDetails(email);
		dto.setEmail(email);
		dto.setUsername(user1.getUsername());
		
		User img=userRepo.findByuseremail(email);
		dto.setFilename(img.getFileName());
		dto.setFileType(img.getFileType());
//		dto.setPicture(img.getProfile());
		
		logger.error("Get user ",dto);
		return new ResponseEntity<UserDTO>(dto,HttpStatus.OK);
	}
	
	
	@PostMapping("/image")
	public ResponseEntity<?> uploadImage( @RequestParam("email") String email,@RequestParam("file") MultipartFile file){

		
		System.out.println(email+" "+file);
		try {
		User added=user.saveFile(email, file);
		if(added!=null)
		 return new  ResponseEntity<User>(added,HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error uploading file",HttpStatus.BAD_GATEWAY);

		}
		catch(IOException e)
		{
			return new ResponseEntity<String>("Error uploading file",HttpStatus.BAD_GATEWAY);
		}

	}
	
	@PostMapping("/password/forgot")
	public ResponseEntity<String> changePassword(@RequestParam String email)
	{
		logger.info("forgot password controller");
		user.generateToken(email);
		return  ResponseEntity.ok("Reset link sent successfully to your mail id!");
	}
	
	public UserController() {
		
	}
	
}
