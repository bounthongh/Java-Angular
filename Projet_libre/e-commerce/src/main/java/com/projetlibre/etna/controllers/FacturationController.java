package com.projetlibre.etna.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.projetlibre.etna.model.Address;
import com.projetlibre.etna.model.Facturation;
import com.projetlibre.etna.model.User.UserRole;
import com.projetlibre.etna.service.FacturationService;
import com.projetlibre.etna.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/facturation")
public class FacturationController {
	
	@Autowired
	private FacturationService facturationService;
	
	@Autowired
	private UserService userService;
	
	private List<Facturation> facturationList = new ArrayList<Facturation>();
	
	@GetMapping()
	public ResponseEntity<List<Facturation>> getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Facturation>>(facturationList , HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
			return new ResponseEntity<List<Facturation>>(facturationService.getList(page - 1, limit), HttpStatus.OK);
		}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<List<Facturation>>(facturationService.getListUser(page - 1, limit,
					userService.currentUser()), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Facturation> getbyID(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}
		
		Facturation facture = facturationService.getOneById(id);
		
		if (facture == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!facture.getAddress().getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}
		
		if((facture.getAddress().getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<Facturation> createFacturation(@RequestBody Facturation facturation){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser() != null) {
			facturation.getAddress().setUser(userService.currentUser());
			return new ResponseEntity<Facturation>(facturationService.create(facturation), HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Facturation> updateFacturation(@PathVariable("id") Integer id, @RequestBody Facturation facturation){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}
		
		Facturation facture = facturationService.getOneById(id);
		
		if(facture == null) {
			return new ResponseEntity<Facturation>(facturation,HttpStatus.NOT_FOUND);
		}
		
		if(!facture.getAddress().getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(facturation, HttpStatus.UNAUTHORIZED);
		}
		
		if(facture != null && (facture.getAddress().getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Facturation>(facturationService.update(id, facturation),HttpStatus.OK);
		}
		
		return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Facturation> deleteFacturation(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}
		
		Facturation facture = facturationService.getOneById(id);
		
		if(facture == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!facture.getAddress().getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.UNAUTHORIZED);
		}
		
		if(facture != null && (facture.getAddress().getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			
			Boolean sucess = facturationService.delete(id);
			
			if(sucess) {
				return new ResponseEntity<Facturation>(HttpStatus.OK);
			}
		}
		
		
		return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Facturation>> search(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Facturation>>(facturationList,HttpStatus.UNAUTHORIZED);
		}
		
		if(search != null && !search.isEmpty()) {
			
			if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Facturation>>(facturationService.getListSearch(page, limit, search), HttpStatus.OK);
			}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Facturation>>(facturationService.getListSearchUser(page, limit, search, userService.currentUser()), HttpStatus.OK);
			}
			
		}else {
			return new ResponseEntity<List<Facturation>>(facturationList,HttpStatus.OK);
		} 
		
		return new ResponseEntity<List<Facturation>>(facturationList,HttpStatus.UNAUTHORIZED);
	}
}
