package com.projetlibre.etna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetlibre.etna.model.JwtUserDetails;
import com.projetlibre.etna.model.User;
import com.projetlibre.etna.repositories.UserRepository;

@Service
public class UserService implements IModelService<User>{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub
		
		PageRequest page = PageRequest.of(object, limit);
		
		return userRepository.getListUserPage(page);
	}

	@Override
	public User getOneById(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<User> user = userRepository.findById(id);
		
		if (user.isPresent()) {
			return user.get();
		}
		
		return null;
	}

	@Override
	public User create(User entity) {
		// TODO Auto-generated method stub
		
		userRepository.save(entity);
	
		return entity;
	}

	@Override
	public User update(Integer id, User entity) {
		// TODO Auto-generated method stub
		if (getOneById(id) == null) {
			return null;
		}
		
		entity.setId(id);
		entity.setCreationDate(getOneById(id).getCreationDate());
		entity.setUpdatedDate(getOneById(id).getUpdatedDate());
		
		userRepository.save(entity);
		
		return entity;
	}



	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		
		try {
			
			if(getOneById(id) == null) {
				return false;
			}
			
			userRepository.delete(getOneById(id));
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return false;
		}
		
		return true;
	}
	
	public User currentUser() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
	
			JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(jwtUserDetails.getUser() != null) {
				return jwtUserDetails.getUser();
			}
		}
		
		return null;
	}

	@Override
	public List<User> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub
		return null;
	}
}