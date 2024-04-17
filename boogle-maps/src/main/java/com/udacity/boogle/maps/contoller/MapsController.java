package com.udacity.boogle.maps.contoller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.boogle.maps.entity.Address;
import com.udacity.boogle.maps.services.MockAddressServices;


@RestController
@RequestMapping("/maps")
public class MapsController {

	@Autowired
	MockAddressServices services;

	@GetMapping
	public List<Address> get(@RequestParam Double lat, @RequestParam Double lon) {
		return services.getAddress(lat,lon);
	}


	@GetMapping("/All")
	public List<Address> getAll() {
		return  services.getAllAddress();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modifyLocation(@PathVariable Long id, @Valid @RequestBody Address add) {
		Address addUpdated=services.save(add,id);   
		return new ResponseEntity<Address> (addUpdated,HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delLocation(@PathVariable Long id) {
		return new ResponseEntity<Long> (services.remove(id),HttpStatus.NO_CONTENT);
		
	}
}

