package com.vedasole.flight_service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@TestConfiguration
@EnableMongoAuditing
@EnableMongoRepositories
public class MongoDBConfig {
    @Bean
    public AuditorAware<String> yAuditorProvider() {
        return () -> Optional.of("test");
    }
}