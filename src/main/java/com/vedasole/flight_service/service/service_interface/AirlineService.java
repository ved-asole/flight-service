package com.vedasole.flight_service.service.service_interface;

import com.vedasole.flight_service.payload.AirlineDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AirlineService {

    AirlineDto createAirline(AirlineDto airlineDto);

    AirlineDto updateAirline(String airlineId, AirlineDto airlineDto);

    AirlineDto deleteAirline(String airlineId);

    AirlineDto getAirlineById(String airlineId);

    Page<AirlineDto> getAllAirlines(int page, int size, String sortBy, String order);

}