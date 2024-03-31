package com.jeroenreijn.demomicrometerprometheusgrafana;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class OrderService {

	private MeterRegistry meterRegistry;

	private Counter orderCounter;

	public OrderService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		this.initOrderCounters();
	}

	private void initOrderCounters() {
		orderCounter = this.meterRegistry.counter("order.purchase"); // 1 - create a counter
	}

	public void orderPurchase() {
		orderCounter.increment(1.0); // 3 - increment the counters
	}

}
