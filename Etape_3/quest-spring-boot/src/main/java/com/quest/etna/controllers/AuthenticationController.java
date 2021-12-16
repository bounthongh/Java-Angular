package com.quest.etna.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.JwtUserDetailsService;
import com.quest.etna.exception.ConflictException;
import com.quest.etna.model.JwtResponse;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;

@RestController
public class AuthenticationController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Value("Authorization")
	private String tokenHeader;

	@Value("Bearer")
	private String tokenPrefix;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> register(@RequestBody User user) {

		// return 500
		if (user.getUsername() == null || user.getPassword() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// return code 409
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists", new ConflictException("409"));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);

		// return code 201
		return new ResponseEntity<UserDetails>(new UserDetails(user), HttpStatus.CREATED);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());
		final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@GetMapping(value = "/me")
	@ResponseBody
	public ResponseEntity<UserDetails> me(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).replace(tokenPrefix, "");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		UserDetails user =  new UserDetails(userRepository.findByUsername(username));
		
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}

	// return 500 is username or password is null and if username or password is null
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadCredentialsException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
