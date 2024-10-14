package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.entity.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepo extends MongoRepository<Airport, String> {

    boolean existsByAirportName(String airportName);

    boolean existsByIataCode(String iataCode);

    long countByAirportNameAndAirportIdNot(String airportName, String airportId);

    long countByIataCodeAndAirportIdNot(String iataCode, String airportId);

}