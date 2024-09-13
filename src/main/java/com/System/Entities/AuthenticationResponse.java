package com.System.Entities;

import com.System.DTOS.UserDTO;

public class AuthenticationResponse {
	
	private UserDTO userdto;
	private String token;
	public AuthenticationResponse() {
		// TODO Auto-generated constructor stub
	}
	public UserDTO getUserdto() {
		return userdto;
	}
	public void setUserdto(UserDTO userdto) {
		this.userdto = userdto;
	}
	public String getToken() {
		return token;
	}
	public AuthenticationResponse(UserDTO userdto, String token) {
		super();
		this.userdto = userdto;
		this.token = token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
