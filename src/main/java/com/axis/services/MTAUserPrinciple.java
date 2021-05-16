package com.axis.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.axis.model.MTAUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MTAUserPrinciple implements UserDetails {
	
	private static final Long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String contactNo;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	public MTAUserPrinciple(Long id, String name, String email, String contactNo, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static MTAUserPrinciple build(MTAUser user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> 
				new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		
		return new MTAUserPrinciple(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getContactNo(),
				user.getUsername(),
				user.getPassword(),
				authorities);
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNo() {
		return contactNo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
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
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		MTAUserPrinciple user = (MTAUserPrinciple) obj;
		return Objects.equals(id, user.id);
	}

}
