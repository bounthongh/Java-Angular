package com.quest.etna.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quest.etna.model.User;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<List<User>> getList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "100") Integer limit){
		if (userService.currentUser() == null)
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		if (userService.currentUser().getRole() == UserRole.ROLE_ADMIN || userService.currentUser().getRole() == UserRole.ROLE_USER ) {
			List<User> l = userService.getList(page, limit);
			return new ResponseEntity<List<User>>(l, HttpStatus.OK);
		}

		return new ResponseEntity<List<User>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getOneById(@PathVariable("id") Integer id){
		if (userService.currentUser() == null)
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);

		User user = userService.getOneById(id);


		if (userService.currentUser() == null)
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
		if (userService.currentUser() == null || userService.currentUser().getRole() != UserRole.ROLE_ADMIN)
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);

		if (userService.currentUser().getId().equals(id) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
			User newUser = userService.getOneById(id);
			newUser.setUsername(user.getUsername());
			newUser.setRole(user.getRole());
			User updatedUser = userService.update(id, newUser);
			if (updatedUser == null)
				return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id){
		if (userService.currentUser() == null)
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);

		if ((userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			Boolean success = userService.delete(id);
			
			if (success)
				return new ResponseEntity<User>(HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
}
