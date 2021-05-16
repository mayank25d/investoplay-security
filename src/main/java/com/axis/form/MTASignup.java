package com.axis.form;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MTASignup {

	@NotBlank
	@Size(min=3, max=100)
	private String name;
	
	@NotBlank
	@Size(min=3, max=100)
	@Email
	private String email;
	
	@NotBlank
	@Size(min=10, max=20)
	private String contactNo;
	
	@NotBlank
	@Size(min=3, max=100)
	private String username;
	
	@NotBlank
	@Size(min=6, max=100)
	private String password;
	
	private Set<String> role;

	public MTASignup(String name, String email,	String contactNo, String username, String password, Set<String> role) {
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}
