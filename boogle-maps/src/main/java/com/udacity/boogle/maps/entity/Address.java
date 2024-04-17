package com.udacity.boogle.maps.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Declares a class to store an address, city, state and zip code.
 */

@Entity
public class Address {

	
	@Id
	@GeneratedValue
	private Long vehicleId; 
	private Double lat;
	private Double lon;
    private String address;
    private String city;
    private String state;
    private String zip;
 

    public Address() {
    }

    
    public Address(String address, String city, String state, String zip) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
	

	public Long getVehicleId() {
		return vehicleId;
	}


	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}





	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}


	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
