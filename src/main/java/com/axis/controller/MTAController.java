package com.axis.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.form.MTALogin;
import com.axis.form.MTASignup;
import com.axis.form.response.MTALoginResponse;
import com.axis.form.response.MTAResponseMessage;
import com.axis.model.MTARole;
import com.axis.model.MTARoleName;
import com.axis.model.MTAUser;
import com.axis.repository.MTARoleRepository;
import com.axis.repository.MTAUserRepository;
import com.axis.services.MTAUserDetailsService;
import com.axis.util.JwtUtil;

//@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class MTAController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MTAUserRepository userRepo;
	
	@Autowired
	private MTARoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/signin")
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody MTALogin login) throws Exception {
		// to create token, we use AuthenticationManager's authenticate method and create a token from
		// username and password
		
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenUtil.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		// now we return the response that is our token
		return ResponseEntity.ok(new MTALoginResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody MTASignup signup) {
		if(userRepo.existsByUsername(signup.getUsername())) {
			return new ResponseEntity<>(new MTAResponseMessage("Fail -> Username Already Exist"), 
					HttpStatus.BAD_REQUEST);
		}
		
		if(userRepo.existsByEmail(signup.getEmail())) {
			return new ResponseEntity<>(new MTAResponseMessage("Fail -> An account is already link with this email"),
					HttpStatus.BAD_REQUEST);
		}
		
		// creating new user's account
		MTAUser user = new MTAUser(signup.getName(), signup.getEmail(), 
				signup.getContactNo(), signup.getUsername(),
				encoder.encode(signup.getPassword()));
		
		Set<String> strRoles = signup.getRole();
		Set<MTARole> roles = new HashSet<>();
		
		if(strRoles == null) {
			MTARole userRole = roleRepo.findByName(MTARoleName.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
			switch(role) {
				case "admin":
					MTARole adminRole = roleRepo.findByName(MTARoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User role not found"));
					roles.add(adminRole);
					
					break;
				/*
				 * case "pm": MTARole pmRole = roleRepo.findByName(MTARoleName.ROLE_PM)
				 * .orElseThrow(() -> new
				 * RuntimeException("Fail! -> Cause: User role not found")); roles.add(pmRole);
				 * 
				 * break;
				 */
				default:
					MTARole userRole = roleRepo.findByName(MTARoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User role not found"));
					roles.add(userRole);
					
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userRepo.save(user);
		
		return new ResponseEntity<>(new MTAResponseMessage("User Registered Successfully!"), HttpStatus.OK);
	}
}
