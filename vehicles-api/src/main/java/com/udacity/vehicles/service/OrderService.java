package com.udacity.vehicles.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.vehicles.client.order.OrderClient;
import com.udacity.vehicles.client.order.Orders;
import com.udacity.vehicles.exception.OrderException;

@Service
public class OrderService {


	@Autowired
	OrderClient client;

	public List<Orders> getDetByVehId(Long vId, Long custId) {
		if(vId>0 && custId>0) {
			return client.getOrderDetail(vId,custId);
		}
		else throw new OrderException("Invalid Request");

	}

	public Orders save(Orders order) {
	
		Orders saved=client.saveOrDet(order);
		
		if(!(saved.getOrderId()>0)) {
			throw new OrderException("Order Details Failed");
		}
	
		return saved;	
	}

}
