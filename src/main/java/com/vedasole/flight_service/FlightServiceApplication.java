package com.vedasole.flight_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableMongoRepositories
@Slf4j
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class FlightServiceApplication {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${project.name:CrimsonSky}")
	private String projectName;

	Environment environment;

	public FlightServiceApplication(Environment environment) {
		this.environment = environment;
    }

	public static void main(String[] args) {
		SpringApplication.run(FlightServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		log.info("FlightServiceApplication started \uD83D\uDE80");
		log.info("Project name: {}, Application name: {}, Port:{}", projectName, appName, environment.getProperty("local.server.port"));
	}

}
