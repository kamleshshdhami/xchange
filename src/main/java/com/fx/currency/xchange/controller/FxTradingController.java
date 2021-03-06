package com.fx.currency.xchange.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fx.currency.xchange.service.FxService;

@RestController
@RequestMapping(value = "/fx/xchange/v1", produces = "application/json; charset=UTF-8")
public class FxTradingController {

	@Autowired
	private FxService fxService;

	@GetMapping("/order/{orderId}")
	public ResponseEntity<FxOrder> retrieveOrder(@PathVariable String orderId) {
		return ResponseEntity.ok().body(fxService.retrieveOrderById(orderId));
	}

	@PostMapping("/order")
	public ResponseEntity<FxOrder> createFxOrder(@Valid @RequestBody FxOrder fxOrder)
			throws URISyntaxException {

		String orderId = fxService.createfxOrder(fxOrder);
		fxService.matchOrder();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(orderId).toUri();
		
		
		return ResponseEntity.created(location).body(new FxOrder(orderId));
	}

	@GetMapping("/order/summary")
	public ResponseEntity<List<FxOrderSummary>> retrieveAllOrderSummary() {
		return ResponseEntity.ok().body(fxService.retrieveAllOrderSummary());
	}

}
