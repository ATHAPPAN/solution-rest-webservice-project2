package com.udacity.boogle.maps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udacity.boogle.maps.entity.Address;

/**
 * Implements a mock repository for generating a random address.
 */
@Repository
public interface MockAddressRepository extends CrudRepository<Address, Long>{
	
	@Query(value="Select D.vehicle_id,D.lat,D.lon,D.address,D.city,D.state,D.zip from  Address D where D.lat=(:lat) AND D.lon=(:lon)",nativeQuery = true)
	List<Address> getLoctionDet(@Param("lat") Double lat, @Param("lon") Double lon);	

}
