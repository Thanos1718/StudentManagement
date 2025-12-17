package com.System.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.System.Entities.User;
import com.System.Exceptions.RecordNotFoundException;
import com.System.Repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	public static final Logger logger=LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository  userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String gmail) throws UsernameNotFoundException {
		logger.info("load {}",gmail);
		User user= userRepo.findByuseremail(gmail);
		if(user==null)
			throw new RecordNotFoundException("User not found");
		return user;			

	}

}
