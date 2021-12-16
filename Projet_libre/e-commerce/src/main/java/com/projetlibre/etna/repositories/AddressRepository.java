package com.projetlibre.etna.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.projetlibre.etna.model.Address;
import com.projetlibre.etna.model.User;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {
	
	@Query("SELECT a FROM Address a WHERE a.User = :User")
	public List<Address> findAllByUser(User User);
	
	@Query("SELECT a FROM Address a WHERE (a.road = :search OR a.city = :search OR a.postalCode = :search OR a.country = :search) ORDER BY a.id ASC")
	public List<Address> search(Pageable page, String search);
	
	@Query("SELECT a FROM Address a WHERE (a.road = :search OR a.city = :search OR a.postalCode = :search OR a.country = :search) AND a.User = :User ORDER BY a.id ASC")
	public List<Address> searchByUser(Pageable page, String search, User User);
	
	@Query("SELECT a FROM Address a ORDER BY a.id ASC")
	public List<Address> findAllByPageUser(Pageable page);
	
	@Query("SELECT a FROM Address a WHERE a.User = :User ORDER BY a.id ASC")
	public List<Address> findAllByUserPage(Pageable page, User User);
}
