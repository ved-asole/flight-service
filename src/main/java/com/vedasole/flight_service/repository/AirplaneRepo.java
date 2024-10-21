package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.entity.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepo extends MongoRepository<Airplane, String> { }