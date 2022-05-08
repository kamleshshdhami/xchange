package com.fx.currency.xchange.contoller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.currency.xchange.controller.FxOrder;
import com.fx.currency.xchange.controller.FxOrderSummary;
import com.fx.currency.xchange.controller.FxTradingController;
import com.fx.currency.xchange.service.FxService;
import com.fx.currency.xchange.util.FxConstant;

@WebMvcTest(FxTradingController.class)
public class FxTradingControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	FxService fxService;

	private FxOrder fxOrder;
	private FxOrderSummary fxOrderSummary;
	private List<FxOrderSummary> fxOrderSummaryList;

	@Test
	void testRetrieveOrder() throws Exception {
		fxOrder = new FxOrder("1", "23.45", "USD");

		Mockito.when(fxService.retrieveOrderById(Mockito.anyString())).thenReturn(fxOrder);
		mockMvc.perform(MockMvcRequestBuilders.get("/fx/xchange/v1/order/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is("1")))
				.andExpect(jsonPath("$.amount", is("23.45"))).andExpect(jsonPath("$.currencyType", is("USD")));

	}

	@Test
	void testRetrieveAllOrderSummary() throws Exception {

		fxOrderSummaryList = new ArrayList<>();

		fxOrderSummary = new FxOrderSummary(1, FxConstant.PENDING, new ArrayList<>());
		fxOrderSummaryList.add(fxOrderSummary);

		Mockito.when(fxService.retrieveAllOrderSummary()).thenReturn(fxOrderSummaryList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/fx/xchange/v1/order/summary").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].orderId", is(1)))
				.andExpect(jsonPath("$[0].orderStatus", is("PENDING")));

	}

	@Test
	void testCreateFxOrder() throws Exception {

		fxOrder = new FxOrder(null, "23.45", "USD");

		Mockito.when(fxService.createfxOrder(Mockito.any())).thenReturn("1");

		mockMvc.perform(MockMvcRequestBuilders.post("/fx/xchange/v1/order").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(fxOrder)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is("1")));

	}

}
