package com.vedasole.flight_service.service.service_interface;

import com.vedasole.flight_service.payload.AirplaneDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AirplaneService {

    AirplaneDto createAirplane(AirplaneDto airplaneDto);

    AirplaneDto updateAirplane(String airplaneId, AirplaneDto airplaneDto);

    AirplaneDto deleteAirplane(String airplaneId);

    AirplaneDto getAirplaneById(String airplaneId);

    Page<AirplaneDto> getAllAirplanes(int page, int size, String sortBy, String order);

}