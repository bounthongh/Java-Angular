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
import com.projetlibre.etna.model.Produit;
import com.projetlibre.etna.model.User.UserRole;
import com.projetlibre.etna.service.ProduitService;
import com.projetlibre.etna.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/produit")
public class ProduitController {
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private UserService userService;
	
	private List<Produit> produitList = new ArrayList<Produit>();
	
	@GetMapping()
	public ResponseEntity<List<Produit>> getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Produit>>(produitList , HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
			return new ResponseEntity<List<Produit>>(produitService.getList(page - 1, limit), HttpStatus.OK);
		}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<List<Produit>>(produitService.getListUser(page - 1, limit,
					userService.currentUser()), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<Produit>>(produitList, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produit> getbyID(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
		}
		
		Produit produit = produitService.getOneById(id);
		
		if (produit == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!produit.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
		}
		
		if((produit.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Produit>(produit, HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<Produit> createProduit(@RequestBody Produit produit){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
		}
		
		if(userService.currentUser() != null) {
			produit.setUser(userService.currentUser());
			return new ResponseEntity<Produit>(produitService.create(produit), HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produit> updateProduit(@PathVariable("id") Integer id, @RequestBody Produit produit){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
		}
		
		Produit product = produitService.getOneById(id);
		
		if(product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!product.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Produit>(produit, HttpStatus.UNAUTHORIZED);
		}
		
		if(product != null && (product.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Produit>(produitService.update(id, produit),HttpStatus.OK);
		}
		
		return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Produit> deleteProduit(@PathVariable("id") Integer id){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
		}
		
		Produit product = produitService.getOneById(id);
		
		if(product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(!product.getUser().equals(userService.currentUser()) && userService.currentUser().getRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Produit>(product, HttpStatus.UNAUTHORIZED);
		}
		
		if(product != null && (product.getUser().equals(userService.currentUser()) || userService.currentUser().getRole() == UserRole.ROLE_ADMIN)) {
			
			Boolean sucess = produitService.delete(id);
			
			if(sucess) {
				return new ResponseEntity<Produit>(HttpStatus.OK);
			}
		}
		
		
		return new ResponseEntity<Produit>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Produit>> search(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search){
		
		if(userService.currentUser() == null) {
			return new ResponseEntity<List<Produit>>(produitList,HttpStatus.UNAUTHORIZED);
		}
		
		if(search != null && !search.isEmpty()) {
			
			if(userService.currentUser().getRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Produit>>(produitService.getListSearch(page, limit, search), HttpStatus.OK);
			}else if(userService.currentUser().getRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Produit>>(produitService.getListSearchUser(page, limit, search, userService.currentUser()), HttpStatus.OK);
			}
			
		}else {
			return new ResponseEntity<List<Produit>>(produitList,HttpStatus.OK);
		} 
		
		return new ResponseEntity<List<Produit>>(produitList,HttpStatus.UNAUTHORIZED);
	}
}
