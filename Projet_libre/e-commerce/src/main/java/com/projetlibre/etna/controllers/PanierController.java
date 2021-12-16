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
import com.projetlibre.etna.model.Panier;
import com.projetlibre.etna.model.User.UserRole;
import com.projetlibre.etna.service.PanierService;
import com.projetlibre.etna.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/panier")
public class PanierController {
	
	@Autowired
	private PanierService panierService;
	
	@Autowired
	private UserService userService;
	
	private List<Panier> panierList = new ArrayList<Panier>();
	
	@GetMapping()
	public ResponseEntity<List<Panier>> getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Panier>>(panierList , HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
			return new ResponseEntity<List<Panier>>(panierService.getList(page - 1, limit), HttpStatus.OK);
		}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<List<Panier>>(panierService.getListUser(page - 1, limit,
					userService.currentUser()), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Panier>>(panierList, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Panier> getbyID(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
		}
		
		Panier panier = panierService.getOneById(id);
		
		if (panier == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!panier.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
		}
		
		if((panier.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Panier>(panier, HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<Panier> createPanier(@RequestBody Panier panier){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser() != null) {
			panier.setUser(userService.currentUser());
			return new ResponseEntity<Panier>(panierService.create(panier), HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Panier> updatePanier(@PathVariable("id") Integer id, @RequestBody Panier panier){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
		}
		
		Panier panier_basket = panierService.getOneById(id);
		
		if(panier_basket == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!panier_basket.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Panier>(panier, HttpStatus.UNAUTHORIZED);
		}
		
		if(panier_basket != null && (panier_basket.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Panier>(panierService.update(id, panier),HttpStatus.OK);
		}
		
		return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Panier> deletePanier(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
		}
		
		Panier panier = panierService.getOneById(id);
		
		if(panier == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!panier.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Panier>(panier, HttpStatus.UNAUTHORIZED);
		}
		
		if(panier != null && (panier.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			
			Boolean sucess = panierService.delete(id);
			
			if(sucess) {
				return new ResponseEntity<Panier>(HttpStatus.OK);
			}
		}
		
		
		return new ResponseEntity<Panier>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Panier>> search(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Panier>>(panierList,HttpStatus.UNAUTHORIZED);
		}
		
		if(search != null && !search.isEmpty()) {
			
			if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Panier>>(panierService.getListSearch(page, limit, search), HttpStatus.OK);
			}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Panier>>(panierService.getListSearchUser(page, limit, search, userService.currentUser()), HttpStatus.OK);
			}
			
		}else {
			return new ResponseEntity<List<Panier>>(panierList,HttpStatus.OK);
		} 
		
		return new ResponseEntity<List<Panier>>(panierList,HttpStatus.UNAUTHORIZED);
	}
}