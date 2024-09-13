package com.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.System.Entities.User;
import com.System.Enum.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
	
	User findByuserRole(UserRole admin);
	User findByusername(String username);
	User findByuseremail(String email);
}
