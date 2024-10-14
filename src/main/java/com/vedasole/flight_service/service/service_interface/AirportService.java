package com.vedasole.flight_service.service.service_interface;

import com.vedasole.flight_service.payload.AirportDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AirportService {

    AirportDto addAirport(AirportDto airportDto);

    AirportDto updateAirport(String airportId, AirportDto airportDto);

    AirportDto deleteAirport(String airportId);

    AirportDto getAirportById(String airportId);

    Page<AirportDto> getAllAirports(int page, int size, String sortBy, String order);

}