package com.udacity.pricing.repossitory;

import org.springframework.stereotype.Repository;

import com.udacity.pricing.domain.price.Price;

import org.springframework.data.repository.CrudRepository;


@Repository
public interface PriceRepository extends CrudRepository<Price,Long> {

}
