package com.quest.etna.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {
	
	@Query("SELECT a FROM Address a WHERE a.User = :User")
	public List<Address> findAllByUser(User User);
	
	@Query("SELECT a FROM Address a WHERE a.road = :road")
	public List<Address> findAllByRue(String road);
	
	@Query("SELECT a FROM Address a WHERE a.postalCode = :postalCode")
	public List<Address> findAllByPostalCode(String postalCode);
	
	@Query("SELECT a FROM Address a WHERE a.city = :city")
	public List<Address> findAllByCity(String city);
	
	@Query("SELECT a FROM Address a WHERE a.country = :country")
	public List<Address> findAllByCountry(String country);
	
	@Query("SELECT a FROM Address a ORDER BY a.id ASC")
	public List<Address> findAllByPageUser(Pageable page);
	
	@Query("SELECT a FROM Address a WHERE a.User = :User ORDER BY a.id ASC")
	public List<Address> findAllByUser(Pageable page, User User);
}
