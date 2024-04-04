package com.udacity.pricing.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.service.PricingService;
@WebMvcTest(PricingController.class)
public class PricieApiUnitTest {

	  @Autowired
	    private MockMvc mockMvc;
	  
	  @MockBean
	  PricingService pricingService;
	  
	  @Test
	  public void getPriceDetailsPostive() throws Exception{
		  
		  //http://localhost:8082/services/price?vehicleId=1
		  //{"currency":"USD","price":17064.95,"vehicleId":1}
		  mockMvc.perform(get("/services/price").queryParam("vehicleId", "1"))
		   .andExpect(status().isOk());
		  
	  }
	  
	  @Test
	  public void getPriceDetailNegative() throws Exception{
		  
		  //http://localhost:8082/services/price?vehicleId=100
		  //org.springframework.web.server.ResponseStatusException: 404 NOT_FOUND "Price Not Found"
		  mockMvc.perform(get("/services/price").queryParam("vehicleId", "100"))
		   .andExpect(status().isNotFound());
		  
	  }
	  
}
