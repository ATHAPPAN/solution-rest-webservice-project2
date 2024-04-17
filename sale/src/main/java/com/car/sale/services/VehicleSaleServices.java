package com.car.sale.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.sale.bean.Orders;
import com.car.sale.exception.OrderDetailException;
import com.car.sale.repository.VehicleRepo;

@Service
public class VehicleSaleServices {

	@Autowired
	private VehicleRepo repo;
	
	public Orders getDetByOrderId(Long id) {
	
		return repo.findById(id).map( s->{
			return repo.findById(id).get();
		}).orElseThrow(OrderDetailException::new);
	}

	public List<Orders> getDetByVehId(Long vId, Long custId) {
		if(repo.findtByVehId(vId,custId).size()>=1)
			return repo.findtByVehId(vId,custId);
		else 
			throw new OrderDetailException("Vehicle not found. Pls search with Order ID");
	}
	public Orders save(Orders order) {
		if(repo.findtByVehId(order.getVehicleId(), order.getCustomerId()).stream().filter(o->o.getStatus().equals("Booked")).count()==0)
			return repo.save(order);
		else throw new OrderDetailException("Out of stock. Pls try again");
		
	}

	public List<Orders> getAll() {
		List<Orders> result= new ArrayList<>();
		 repo.findAll().forEach(o->result.add(o));
		 return result;
	}

}
