package com.project.questapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.business.concretes.UserManager;
import com.project.questapp.dataAccess.abstracts.UserDao;
import com.project.questapp.entities.User;
import com.project.questapp.requests.UserRequest;
import com.project.questapp.security.JwtTokenProvider;



@RestController
@RequestMapping("/auth")

public class AuthenticationController {


	private  AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, 
			UserService userService, PasswordEncoder passwordEncoder) {

		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public String login(@RequestBody UserRequest userLoginRequest) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword());
		
		Authentication auth = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		return "Bearer "+jwtToken;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest userRegisterRequest){
		
		if (userService.getOneUserByUserName(userRegisterRequest.getUserName()) != null) {
			return new ResponseEntity<>("Username alredy in use!",HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setUserName(userRegisterRequest.getUserName());
		user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
		userService.saveOneUser(user);
		
		return new  ResponseEntity<>("User Succesfully registered!",HttpStatus.CREATED);
		
	}
	
}
