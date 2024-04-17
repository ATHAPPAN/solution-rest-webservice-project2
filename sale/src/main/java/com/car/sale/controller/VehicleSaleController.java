package com.car.sale.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.sale.bean.Orders;
import com.car.sale.services.VehicleSaleServices;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class VehicleSaleController {

	@Autowired
	private VehicleSaleServices services;
	
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getOrderDetails(){
		return new ResponseEntity<>(services.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/order/{id}")
	public ResponseEntity<Orders> getOrderDetails(@PathVariable("id") Long id){
		return new ResponseEntity<>(services.getDetByOrderId(id),HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Orders>> getOrderDet(@RequestParam Long vId, @RequestParam Long custId){
		return new ResponseEntity<>(services.getDetByVehId(vId,custId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Orders order){
		return new ResponseEntity<>(services.save(order),HttpStatus.ACCEPTED);
	}
}
