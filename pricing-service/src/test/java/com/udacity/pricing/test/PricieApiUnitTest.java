package com.udacity.pricing.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.repossitory.PriceRepository;
import com.udacity.pricing.service.PricingService;

@RunWith(SpringRunner.class)
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

		verify(pricingService,times(1)).getPrice(1L);
	}

}
