package com.udacity.vehicles.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.vehicles.client.order.Orders;
import com.udacity.vehicles.service.OrderService;



@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService services;
	
	@GetMapping
	public ResponseEntity<?> getOrderDet(@RequestParam Long vId, @RequestParam Long custId){
		return new ResponseEntity<>(services.getDetByVehId(vId,custId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Orders order){
		return new ResponseEntity<>(services.save(order),HttpStatus.ACCEPTED);
	}
}
