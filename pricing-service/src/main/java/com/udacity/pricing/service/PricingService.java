package com.udacity.pricing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.repossitory.PriceRepository;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {

	@Autowired
	PriceRepository priceRepository;
    /**
     * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
     */
	
	@SuppressWarnings("unused")
	//Impl is not required , Current implementation is capable to store price object to H2 DB
	@Deprecated
    private  final Map<Long, Price> PRICES = LongStream
            .range(1, 20)
            .mapToObj(i -> new Price("USD", randomPrice(), i))
            .collect(Collectors.toMap(Price::getVehicleId, p -> p));

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public Price getPrice(Long vehicleId) throws PriceException {

        if (priceRepository.findById(vehicleId).isEmpty()) {
            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
        }

        return priceRepository.findById(vehicleId).get();
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private  BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

	public  Price savePrice( Price price,Long vehicleId) throws PriceException {
		Price priceTUpd= new Price("INR",randomPrice(),vehicleId);
		if(priceRepository.findById(vehicleId).isEmpty()) {
			return  priceRepository.save(price);
		}
		else return  priceRepository.save(priceTUpd);
	}


	public  Long deletePrice(Long vehicleId) throws PriceException {
		System.out.println("Serviec "+vehicleId);
		return priceRepository.findById(vehicleId).map( p->{
			   priceRepository.deleteById(vehicleId);
			   return vehicleId;
		}).orElseThrow(PriceException::new);
	}

}
