package com.vedasole.flight_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class FlightServiceApplicationTests {

	@Autowired
	private FlightServiceApplication flightServiceApplication;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(SpringApplication.class);
		Assertions.assertNotNull(FlightServiceApplication.class);
		Assertions.assertNotNull(flightServiceApplication);
    }

	@Test
	void testApplicationStartsSuccessfully() {
		// Arrange
		ApplicationContext context = SpringApplication.run(FlightServiceApplication.class);

		// Act
		boolean isRunning = context.containsBean("flightServiceApplication");

		// Assert
		Assertions.assertTrue(isRunning, "FlightServiceApplication did not start successfully");
	}

}
