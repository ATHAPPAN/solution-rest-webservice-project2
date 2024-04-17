package com.car.sale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.car.sale.bean.Orders;

@Repository
public interface VehicleRepo extends CrudRepository<Orders, Long> {

	@Query(value="Select D.order_id,D.customer_id,D.vehicle_id,D.status  from  Orders D where D.vehicle_id=(:vId) and D.customer_id=(:custId)",nativeQuery = true)
	public List<Orders>  findtByVehId(Long vId, Long custId);

}
