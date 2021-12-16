package com.quest.etna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.exception.ConflictException;
import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;

@RestController
public class AuthenticationController{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> register(@RequestBody User user) {		
		
		//return 500
		if (user.getUsername() == null || user.getPassword() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//return code 409
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"User already exists", new ConflictException("409"));
		}

		userRepository.save(user);
		
		//return code 201
		return new ResponseEntity<UserDetails>(new UserDetails(user),HttpStatus.CREATED);
	}
	
	
	
}