package com.udacity.boogle.maps.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.boogle.maps.entity.Address;
import com.udacity.boogle.maps.exception.LocationException;
import com.udacity.boogle.maps.repository.MockAddressRepository;


@Service
public class MockAddressServices {
	private static final Logger log = LoggerFactory.getLogger(MockAddressServices.class);
	@Autowired
	MockAddressRepository repo;



	public List<Address> getAddress(Double lat,  Double lon) {		 
		return repo.getLoctionDet(lat,lon);
	}



	public List<Address> getAllAddress() {		
		List<Address> allLoc= new ArrayList<>();
		repo.findAll().forEach(allLoc :: add);
		return allLoc;
	}



	public Address save( Address add, Long id) {
		List<Address>  addInfo=getAddress(add.getLat(),add.getLon());

		if(addInfo.isEmpty()) 
			throw new LocationException("Location Not found for the Lat nd Lon");

		return repo.findById(id).map(loc->{
			Address toUpda=addInfo.get(0);
			add.setVehicleId(id);
			add.setAddress(toUpda.getAddress());
			add.setCity(toUpda.getCity());
			add.setState(toUpda.getState());
			add.setZip(toUpda.getZip());
			return repo.save(add);
		}).orElseThrow(LocationException :: new);
	}



	public Long remove(Long id) {
		Long result= id;
		//repo.findById(id).ifPresentOrElse(add->repo.deleteById(add.getVehicleId()), LocationException :: new);

		repo.findById(id).ifPresentOrElse(add->
		{
			repo.deleteById(add.getVehicleId());
			
			 //public Address(String address, String city, String state, String zip) {
			Address toUpda=new Address(add.getAddress(),add.getCity(),add.getState(),add.getZip());	
			//toUpda.setVehicleId(-1l);
			toUpda.setLat(add.getLat());
			toUpda.setLon(add.getLon());
			repo.save(toUpda); // detach the vehicle for location
			
		}, LocationException :: new);
		//Log the delete status   
		if(repo.findById(id).isEmpty()) {
			log.info(String.format("The Vehicle Id - %s is removed from Repo", id));
			return id;
		}
		else {
			log.info(String.format("The Vehicle Id - %s is not removed from Repo, Please Retry !!", id));
			result= -1l;
		}

		return result;
	}


}
