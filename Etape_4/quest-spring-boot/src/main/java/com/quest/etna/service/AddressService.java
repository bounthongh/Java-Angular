package com.quest.etna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.repositories.AddressRepository;

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
	
	public List<Address> getListByUser(Integer object, Integer limit, User user) {
		
		PageRequest page = PageRequest.of(object,limit);
		
		return addressRepository.findAllByUser(page,user);
	}
	
}