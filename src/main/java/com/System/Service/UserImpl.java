package com.System.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.System.DTOS.UserDTO;
import com.System.Entities.AuthenticationResponse;
import com.System.Entities.ResetToken;
import com.System.Entities.User;
import com.System.Enum.UserRole;
import com.System.Exceptions.DuplicateRecordException;
import com.System.Exceptions.RecordNotFoundException;
import com.System.Repository.TokenRepo;
import com.System.Repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class UserImpl  {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	private final PasswordEncoder passwordEncoder;


	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;
	
	public UserImpl(AuthenticationManager authenticationManager,JwtService jwtservice,PasswordEncoder passwordEncoder) {
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtservice;
		this.passwordEncoder=passwordEncoder;
	}
//	@Autowired
//	private UserImageRepository imageRepo;
	private static final Logger logger=LoggerFactory.getLogger(UserImpl.class);

	public User addUser(User user)
	{
		User exist=userRepo.findByuseremail(user.getUseremail());
		if(exist!=null)
			throw new DuplicateRecordException("User with email already exist : "+user.getUseremail());
		return userRepo.save(user);
	}
	
	@PostConstruct
	public void setAdmin()
	{
		System.out.println("user called");
		User user=userRepo.findByuserRole(UserRole.ADMIN);
		if(user==null)
		{
			User userAdmin=new User();
			userAdmin.setUseremail("admin@gmail.com");
			userAdmin.setPassword(passwordEncoder.encode("Admin@123"));
			userAdmin.setUsername("Admin12");
			userAdmin.setUserRole(UserRole.ADMIN);
			userRepo.save(userAdmin);
		}
	}
	
	
	public User saveFile(String email,MultipartFile file) throws IOException
	{
		Optional<User> user=Optional.ofNullable(userRepo.findByuseremail(email));
		if(user.isPresent())
		{
			 User usera = user.get();
	            String fileName = file.getOriginalFilename();
	            String fileType = file.getContentType();
	            byte[] fileData = file.getBytes();

	            usera.setFileName(fileName);
	            usera.setFileType(fileType);
//	            usera.setProfile(fileData);

	            return userRepo.save(usera);
		}
		else
			throw new RecordNotFoundException("User not registered!");
	}
	
	public User getUser(String email)
	{
		Optional<User> optional=Optional.ofNullable(userRepo.findByuseremail(email));
		if(optional.isPresent())
			return optional.get();
		else
			throw new RecordNotFoundException("User not registered");
	}
	public void generateToken(String email)
	{
		logger.info("generate method");
		Optional<User> user=Optional.ofNullable(userRepo.findByuseremail(email));
		if(user.isPresent())
		{
			String token=UUID.randomUUID().toString();
			
			ResetToken resetToken=new ResetToken();
			resetToken.setToken(token);
			resetToken.setUser(user.get());
			resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(10));
            tokenRepo.save(resetToken);

			 String reseturl = "http://localhost:4200/reset-password?token=" + token;
	            emailService.sendSimpleMessage(email, "Reset Password", "Click the link to reset your password: " + reseturl);
		}
	}
	
	public boolean validateToken(String token){
		ResetToken reset= tokenRepo.findByToken(token);
		
		return reset!=null && reset.getExpirationDate().isAfter(LocalDateTime.now());
	}
	
	public void resetPassword(String token, String newPassword) {
        ResetToken resetToken = tokenRepo.findByToken(token);
        if (validateToken(token)) {
            User user = resetToken.getUser();
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepo.save(user);
            tokenRepo.delete(resetToken);
        }
	}
	
	@Transactional
	public User getDetails(String email) {
		Optional<User> user=Optional.ofNullable(userRepo.findByuseremail(email));
		if(user.isPresent())
		{
			logger.error("user {}",user.get());
			return user.get();
		}
		else
			throw new RecordNotFoundException("User does not exist!");
		
	}
	public AuthenticationResponse authenticate(UserDTO request) {
		 authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		System.out.println("Authentication Manager Auth Success");
		User login = userRepo.findByuseremail(request.getEmail());
		
		UserDTO dto=new UserDTO();
		dto.setEmail(login.getUseremail());
		dto.setRole(login.getUserRole());
	
		System.out.println("UserName is" + login.getUsername());
		String token = jwtService.genrateToken(login);
		
		logger.error("token {}",token);
		return new AuthenticationResponse( dto,token);
	}

	
	

}
