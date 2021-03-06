package com.axis.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
public class MTAUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=3, max=100)
	private String name;
	
	@NaturalId
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
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<MTARole> roles = new HashSet<>();
	
	public MTAUser() {
		
	}

	public MTAUser(String name, String email, String contactNo, String username, String password) {
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<MTARole> getRoles() {
		return roles;
	}

	public void setRoles(Set<MTARole> roles) {
		this.roles = roles;
	}
	
}
