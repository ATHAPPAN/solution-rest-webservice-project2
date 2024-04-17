package com.udacity.vehicles.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.exception.CarNotFoundException;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

	private static final Logger log = LoggerFactory.getLogger(CarService.class);
	private final CarRepository repository;

	private final WebClient boogleMap;
	private final WebClient priceApiClient;

	@Autowired
	private  ModelMapper mapper;

	//	    @Autowired
	//	    private PriceClient vehiclePriceClient;   
	//	    @Autowired
	//	    private MapsClient mapsClient;


	public CarService(CarRepository repository, @Qualifier("maps") WebClient boogleMap,  @Qualifier("pricing") WebClient priceApiClient) {
		/**
		 * TODO: Add the Maps and Pricing Web Clients you create
		 * in `VehiclesApiApplication` as arguments and set them here.
		 */
		//Solution : Use the Bean created in VehiclesApiApplication , since we have 2 instance of WebClient using @Qualifier for mapping  
		//Clarification : instead of mapping arguments, can we map the PriceClient and MapsClient bean like line # 39-42 ?
		this.boogleMap=boogleMap;
		this.priceApiClient=priceApiClient;
		this.repository = repository;
	}

	/**
	 * Gathers a list of all vehicles
	 * @return a list of all vehicles in the CarRepository
	 */
	public List<Car> list() {
		List<Car>  result= new ArrayList<Car>();
		 repository.findAll().forEach(c -> {
			 result.add(findById(c.getId()));
		 });
		 return result;
		 //Clarification : It will not fetch Address and Price info
		//return repository.findAll();
	}

	/**
	 * Gets car information by ID (or throws exception if non-existent)
	 * @param id the ID number of the car to gather information on
	 * @return the requested car's information, including location and price
	 */
	public Car findById(Long id) {
		/**
		 * TODO: Find the car by ID from the `repository` if it exists.
		 *   If it does not exist, throw a CarNotFoundException
		 *   Remove the below code as part of your implementation.
		 */

		/*Solution: invoke findById method from CarRepository by passing vehicleId, 
		 * which will return a optional<Car> if the Car object is available in the repo then fetch the Price details from PriceApi 
		 * and Address details from boogleApi , if it does not exist, throw a CarNotFoundException -404
		 */
		Car car=repository.findById(id).map(carFound->{
			PriceClient vehiclePriceClient = new PriceClient(priceApiClient); // this code can be ignore if we use line # 39-42
			String price = vehiclePriceClient.getPrice(id);
			carFound.setPrice(price);	

			MapsClient mapsClient = new MapsClient(boogleMap,mapper);  // this code can be ignore if we use line # 39-42
			Location location= mapsClient.getAddress(carFound.getLocation());
			carFound.setLocation(location);
			return carFound;
		}).orElseThrow(CarNotFoundException:: new);
		System.out.println(car.toString());
		return car;
	}

	/**
	 * Either creates or updates a vehicle, based on prior existence of car
	 * @param car A car object, which can be either new or existing
	 * @return the new/updated car is stored in the repository
	 */
	public Car save(Car car) {
		PriceClient vehiclePriceClient = new PriceClient(priceApiClient); // this code can be ignore if we use line # 39-42
		MapsClient mapsClient = new MapsClient(boogleMap,mapper);  // this code can be ignore if we use line # 39-42
		if (car.getId() != null) {	
			repository.findById(car.getId())
					.map(carToBeUpdated -> {									
						car.setDetails(car.getDetails());
						//notify the Locations changes to boogle Map API 
						car.setLocation(mapsClient.updateAddress(car.getLocation(),car.getId()));
						
						//notify the Price changes to Pricing  API 			
						car.setPrice(vehiclePriceClient.updatePrice(getPriceReq(car.getPrice()),car.getId()));
						return repository.save(car);					
					}).orElseThrow(CarNotFoundException::new);
		}
		else {
			Car updatedcar=repository.save(car);
			//notify the Locations changes to boogle Map API 
			mapsClient.updateAddress(updatedcar.getLocation(),updatedcar.getId());			
			//notify the Price changes to Pricing  API 
			car.setPrice(vehiclePriceClient.updatePrice(getPriceReq(car.getPrice()),car.getId()));
			return updatedcar;
		}
		return car;
	}



	/**
	 * Deletes a given car by ID
	 * @param id the ID number of the car to delete
	 */
	public boolean delete(Long id) {
		/**
		 * TODO: Find the car by ID from the `repository` if it exists.
		 *   If it does not exist, throw a CarNotFoundException
		 */
		/**
		 * TODO: Delete the car from the repository.
		 */
		boolean isDeleted=false;
		PriceClient vehiclePriceClient = new PriceClient(priceApiClient); // this code can be ignore if we use line # 39-42
		MapsClient mapsClient = new MapsClient(boogleMap,mapper);  // this code can be ignore if we use line # 39-42
		
		//Solution : Invoke the deleteById method from car repository by passing given id,  If id does not exist, throw a CarNotFoundException
		repository.findById(id)
		.map(carToBeDel->{
			//notify the Locations changes to boogle Map API
			mapsClient.deleteAddress(carToBeDel.getId());
			repository.deleteById(carToBeDel.getId());
			
			//notify the Price changes to Pricing  API 
			vehiclePriceClient.deletePrice(carToBeDel.getId());		
			
			return carToBeDel;
		}).orElseThrow(CarNotFoundException::new);

	
		//Log the delete status   
		if(repository.findById(id).isEmpty())  isDeleted=true; 
		
		return isDeleted;
		

	}
	
	private double getPriceReq(String price) {
		
		try {
			if(price != null && price.contains(" ")) {
				String amt=price.split(" ")[1];
				if(!amt.isBlank() && amt.matches("\\d+\\.?\\d*")) 
					return 	Double.valueOf(price.split(" ")[1]);			
			}
		} catch (Exception e) {
			log.debug("Default Price set due to Error "+e.getMessage());
		}
		
		return 1000d;
	}
}
