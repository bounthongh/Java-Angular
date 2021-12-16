package com.projetlibre.etna.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.projetlibre.etna.model.Facturation;
import com.projetlibre.etna.model.User;

@Repository
public interface FacturationRepository  extends PagingAndSortingRepository<Facturation, Integer>{
	
	
	@Query("SELECT f FROM Facturation f ORDER BY f.id ASC")
	public List<Facturation> findAllFacturation(Pageable page);
	
	@Query("SELECT f FROM Facturation f WHERE (f.panier.produit.nom = :search OR f.address.User.username = :search) ORDER BY f.id ASC")
	public List<Facturation> search(Pageable page, String search);
	
	@Query("SELECT f FROM Facturation f WHERE address.User = :User ORDER BY f.id ASC")
	public List<Facturation> findByUser(Pageable page, User User);
	
	@Query("SELECT f FROM Facturation f WHERE (f.panier.produit.nom = :search OR f.address.User.username = :search) AND f.address.User = :User ORDER BY f.id ASC")
	public List<Facturation> searchByUser(Pageable page, String search, User User);
}
