package com.axis.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axis.model.MTAUser;
import com.axis.repository.MTAUserRepository;

@Service
public class MTAUserDetailsService implements UserDetailsService {
	
	@Autowired
	MTAUserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		MTAUser user = userRepo.findByUsername(userName).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username: " + userName));
		
		return MTAUserPrinciple.build(user);
	}

	
}
