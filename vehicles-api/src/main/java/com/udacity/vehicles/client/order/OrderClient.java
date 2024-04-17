package com.udacity.vehicles.client.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.exception.OrderException;



@Component
public class OrderClient {

	private static final Logger log = LoggerFactory.getLogger(OrderClient.class);
	@Autowired
	@Qualifier("sale")
	WebClient orderCliWebClient;

	public List<Orders> getOrderDetail(Long vId, Long custId) {
		try {
			//http://localhost:4040/sales?vId=1&custId=1
			List<Orders> orDet = orderCliWebClient
					.get()
					.uri(uriBuilder -> uriBuilder
							.path("/sales")
							.queryParam("vId", vId)
							.queryParam("custId", custId)
							.build()
							) .retrieve().bodyToMono(new ParameterizedTypeReference<List<Orders>>() {}).block();

			return orDet;
			
		} catch (Exception e) {
			log.warn("Order service is down");
			if(e.getMessage() == null) throw new OrderException("Order API is down. Pls Try After sometimes .. !");    
			else throw new OrderException(e.getMessage());    

		}
	}
	
	public Orders saveOrDet(Orders ordTobeSave) {	    
		try {

			Orders orDet = orderCliWebClient.post()
					.uri("/sales")
					.contentType(MediaType.APPLICATION_JSON)
					.syncBody(ordTobeSave)
					.retrieve().bodyToMono(Orders.class).block();

			return orDet;

		} catch (Exception e) {
			log.warn("Order service is down");
			if(e.getMessage() == null) throw new OrderException("Order API is down. Pls Try After sometimes .. !");    
			else throw new OrderException(e.getMessage());    

		} 
	}
}
