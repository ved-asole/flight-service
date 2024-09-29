package com.vedasole.flight_service.service.service_interface;

import com.vedasole.flight_service.payload.CountryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {

    CountryDto addCountry(CountryDto countryDto);

    CountryDto updateCountry(String countryId, CountryDto countryDto);

    CountryDto deleteCountry(String countryId);

    CountryDto getCountryById(String countryId);

    Page<CountryDto> getAllCountries(int page, int size, String sortBy, String order);

}