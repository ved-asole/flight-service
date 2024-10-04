package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.entity.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends MongoRepository<Country, String> {

    boolean existsByCountryName(String countryName);

    boolean existsByCountryIso2(String countryIso2);

}