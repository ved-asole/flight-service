package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends MongoRepository<City, String> {

    boolean existsByIataCode(String iataCode);

    boolean existsByIataCodeAndCityIdNot(String iataCode, String cityId);

}