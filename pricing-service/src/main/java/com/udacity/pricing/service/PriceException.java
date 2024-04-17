package com.udacity.pricing.service;

public class PriceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PriceException(){
		
	}
    public PriceException(String message) {
        super(message);
    }
}
