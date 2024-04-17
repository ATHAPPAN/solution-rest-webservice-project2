package com.udacity.pricing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {

	@Autowired
	PricingService service;
	/**
	 * Gets the price for a requested vehicle.
	 * @param vehicleId ID number of the vehicle for which the price is requested
	 * @return price of the vehicle, or error that it was not found.
	 */
	@GetMapping
	public Price get(@RequestParam Long vehicleId) {
		try {
			return service.getPrice(vehicleId);
		} catch (PriceException ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Price Not Found", ex);
		}

	}


	@PostMapping
	public Price save(@Validated @RequestBody Price price ) {
		try {
			return service.savePrice(price,price.getVehicleId());
		} catch (PriceException ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Price Not Saved", ex);
		}

	}

	@PutMapping
	public Price update(@Validated @RequestBody Price price , @RequestParam Long vehicleId ) {
		try {
			return service.savePrice(price,vehicleId);
		} catch (PriceException ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Price Not Update", ex);
		}

	}


	@DeleteMapping("/{vehicleId}")
	public ResponseEntity<?> delete(@PathVariable Long vehicleId) {
		try {
			System.out.println("=vehicleId c"+vehicleId);
			return new ResponseEntity<Long> (service.deletePrice(vehicleId),HttpStatus.NO_CONTENT);
		} catch (PriceException ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Delete Failed", ex);
		}

	}
}
