package com.quest.etna.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.Address;
import com.quest.etna.service.AddressService;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.service.UserService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService; 
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public ResponseEntity<List<Address>> getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit){
		if (userService.currentUser() == null)
			return new ResponseEntity<List<Address>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);

		if (userService.currentUser().getRole() == UserRole.ROLE_ADMIN)
			return new ResponseEntity<List<Address>>(addressService.getList(page - 1, limit), HttpStatus.OK);
		else if (userService.currentUser().getRole() == UserRole.ROLE_USER){
			return new ResponseEntity<List<Address>>(addressService.getListByUser(page - 1, limit,
					userService.currentUser()), HttpStatus.OK);
		}
		return new ResponseEntity<List<Address>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> getbyID(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Address>(HttpStatus.UNAUTHORIZED);

		Address address = addressService.getOneById(id);

		if (address == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		if (!address.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Address>(address,HttpStatus.UNAUTHORIZED);

		if ((address.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN))
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<Address> createAdresse(@RequestBody Address address) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Address>(address, HttpStatus.UNAUTHORIZED);

		if (userService.currentUser() != null) {
			address.setUser(userService.currentUser());
			return new ResponseEntity<Address>(addressService.create(address), HttpStatus.CREATED);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAdresse(@PathVariable("id") Integer id, @RequestBody Address address) {

		if (userService.currentUser() == null)
			return new ResponseEntity<Address>(address, HttpStatus.UNAUTHORIZED);

		Address Address = addressService.getOneById(id);

		if (Address == null)
			return new ResponseEntity<Address>(address, HttpStatus.NOT_FOUND);

		if (!Address.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Address>(address, HttpStatus.UNAUTHORIZED);

		if (Address != null && (Address.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN))
			return new ResponseEntity<Address>(addressService.update(id, address), HttpStatus.OK);
		return new ResponseEntity<Address>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Address> deleteAdresse(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Address>(HttpStatus.UNAUTHORIZED);

		Address address = addressService.getOneById(id);

		if (address == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		if (!address.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Address>(address, HttpStatus.UNAUTHORIZED);
		
		if (address.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN){
			
			Boolean sucess = addressService.delete(id);
			
			if (sucess)
				return new ResponseEntity<Address>(HttpStatus.OK);
		}
		return new ResponseEntity<Address>(HttpStatus.UNAUTHORIZED);
	}
}