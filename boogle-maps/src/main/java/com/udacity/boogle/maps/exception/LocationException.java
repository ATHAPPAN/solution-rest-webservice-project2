package com.udacity.boogle.maps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Address not found")
public class LocationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocationException() {
    }

    public LocationException(String message) {
        super(message);
    }
}