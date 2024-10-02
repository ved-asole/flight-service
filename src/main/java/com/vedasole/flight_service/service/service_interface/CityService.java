package com.vedasole.flight_service.service.service_interface;

import com.vedasole.flight_service.payload.CityDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CityService {

    CityDto addCity(CityDto cityDto);

    CityDto updateCity(String cityId, CityDto cityDto);

    CityDto deleteCity(String cityId);

    CityDto getCityById(String cityId);

    Page<CityDto> getAllCities(int page, int size, String sortBy, String order);

}