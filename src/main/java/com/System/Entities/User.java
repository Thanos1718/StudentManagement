package com.System.Entities;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.System.Enum.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity

@Table(name="UserTable")
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	@Column(unique=true)
	@NotBlank(message = "Username is mandatory")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username can only contain letters, digits, and underscores")
	private String username;
	@Email
	@Id
	private String useremail;
	
	@NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*", message = "Password must contain at least one uppercase letter, one digit, and one special character")
	@NotEmpty(message="password must not be empty")
	private String password;
	
//
//	@Lob
//	@Column(name="profilePic")
//	private byte[] profile;
	
	private String fileName;
	private String fileType;
//	public byte[] getProfile() {
//		return profile;
//	}
//	public void setProfile(byte[] profile) {
//		this.profile = profile;
//	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	private UserRole userRole;
	
	
	@Override
	public String getUsername() {
		return useremail;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [  username=" + username + ", email=" + useremail + "user picture";
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("log role" + userRole.name());
		return List.of(new SimpleGrantedAuthority(userRole.name()));

	}
	
}
