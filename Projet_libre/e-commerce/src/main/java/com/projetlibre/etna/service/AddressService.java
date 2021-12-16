package com.projetlibre.etna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.projetlibre.etna.model.Address;
import com.projetlibre.etna.model.User;
import com.projetlibre.etna.repositories.AddressRepository;

@Service
public class AddressService implements IModelService<Address> {
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<Address> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub
		
		PageRequest page = PageRequest.of(object,limit);
		
		return addressRepository.findAllByPageUser(page);
	}

	@Override
	public Address getOneById(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<Address> address = addressRepository.findById(id);

		if(!address.isPresent()) {
			return null;
		}
		
		return address.get();
	}

	@Override
	public Address create(Address entity) {
		// TODO Auto-generated method stub
		
		addressRepository.save(entity);
		
		return entity;
	}

	@Override
	public Address update(Integer id, Address entity) {
		// TODO Auto-generated method stub
		
		if(getOneById(id) == null) {
			return null;
		}
		
		entity.setId(id);
		entity.setUser(getOneById(id).getUser());
		entity.setCreationDate(getOneById(id).getCreationDate());
		entity.setUpdatedDate(getOneById(id).getUpdatedDate());
		
		addressRepository.save(entity);
		
		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		
		try {
			
			if(getOneById(id) == null) {
				return false;
			}
			
			addressRepository.delete(getOneById(id));
			
		} catch (Exception e) {
			// TODO: handle exception
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<Address> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub
		
		PageRequest page = PageRequest.of(obj,limit);
		
		return addressRepository.search(page, search);
	}

	@Override
	public List<Address> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub
		
		PageRequest page = PageRequest.of(obj,limit);
		
		
		return addressRepository.findAllByUserPage(page, user);
	}

	@Override
	public List<Address> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub
		
		PageRequest page = PageRequest.of(obj,limit);
		
		return addressRepository.searchByUser(page, search, user);
	}
}