package com.udacity.vehicles.client.maps;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.exception.BoogleApiException;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapsClient {

    private static final Logger log = LoggerFactory.getLogger(MapsClient.class);

    private final WebClient client;
    private final ModelMapper mapper;

    
    public MapsClient(WebClient maps,
            ModelMapper mapper) {
        this.client = maps;
        this.mapper = mapper;
    }

    /**
     * Gets an address from the Maps client, given latitude and longitude.
     * @param location An object containing "lat" and "lon" of location
     * @return An updated location including street, city, state and zip,
     *   or an exception message noting the Maps service is down
     */
    public Location getAddress(Location location) {
        try {
            List<Address> address = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/maps/")
                            .queryParam("lat", location.getLat())
                            .queryParam("lon", location.getLon())
                            .build()
                    ) .retrieve().bodyToMono(new ParameterizedTypeReference<List<Address>>() {}).block();

            if(address == null )throw new BoogleApiException("Address not Found, Pls earch with Vehicle Id");
            else if (address.isEmpty()) throw new BoogleApiException("Address not Found, Pls earch with Vehicle Id");
            else  mapper.map(Objects.requireNonNull(address.get(0)), location);
          
            return location;
        } catch (Exception e) {
            log.warn("Map service is down");
            if(e.getMessage() == null) throw new BoogleApiException("Boogle API is down. Pls Try After sometimes .. !");    
            else throw new BoogleApiException(e.getMessage());    
        	      
        }
    }
    
    
    /**
     * @param location
     * @param vehileId
     * @return
     */
    public Location updateAddress(Location location, Long vehileId ) {
        try {
	
        	Location locUpdated = client.put().
            		uri("/maps/{id}", vehileId)
            		.contentType(MediaType.APPLICATION_JSON)
            		.syncBody(location)
            		.retrieve().bodyToMono(Location.class).block();
            return locUpdated;
        } catch (Exception e) {
            log.warn("Map service is down");
            if(e.getMessage()!= null && e.getMessage().isBlank())
            	throw new BoogleApiException("Boogle API is down. Pls Try After sometimes .. !");         
            else throw new BoogleApiException(e.getMessage());    
        }
    }

	public void deleteAddress(Long vehileId) {
		 try {
			 Long locDeleted= client.delete().
	            		uri("/maps/{id}", vehileId)	.retrieve().bodyToMono(Long.class).block();
	            		
//			if(locDeleted == vehileId) log.info("Location info has been removed for the ID " + vehileId);
//			else  log.warn("Failed to removed the Location info for the ID" + vehileId);
	        } catch (Exception e) {
	        	throw new BoogleApiException("Boogle API is down. Pls Try After sometimes .. !");          
	        }
	    }

//	public Location saveAddress(Location location) {
//	      try {
//	    		
//	        	Location locSaved = client.post().
//	            		uri("/maps")
//	            		.contentType(MediaType.APPLICATION_JSON)
//	            		.syncBody(location)
//	            		.retrieve().bodyToMono(Location.class).block();
//	            return locSaved;
//	        } catch (Exception e) {
//	            log.warn("Map service is down");
//	            throw new BoogleApiException("Boogle API is down. Pls Try After sometimes .. !");          
//	        }
//	    }
	
	
}
