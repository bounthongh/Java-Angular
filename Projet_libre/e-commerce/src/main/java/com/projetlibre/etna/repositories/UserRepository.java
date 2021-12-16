package com.projetlibre.etna.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projetlibre.etna.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	@Query("SELECT u FROM User u WHERE u.username = :name")
	public User findByUsername(String name);

	@Query("SELECT u FROM User u ORDER BY u.id ASC")
	public List<User> getListUserPage(Pageable page);
}