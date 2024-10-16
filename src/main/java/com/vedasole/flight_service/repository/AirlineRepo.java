package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.entity.Airline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepo extends MongoRepository<Airline, String> { }