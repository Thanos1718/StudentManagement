package com.System.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.System.DTOS.UserDTO;
import com.System.Entities.Student;
import com.System.Entities.User;
import com.System.Enum.UserRole;
import com.System.Service.StudentImpl;

@RestController
@RequestMapping("/register")
public class Register {

	
	
	public Register() {
		// TODO Auto-generated constructor stub
	}

}
