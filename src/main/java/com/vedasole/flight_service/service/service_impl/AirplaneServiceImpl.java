package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Airplane;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.AirplaneDto;
import com.vedasole.flight_service.repository.AirplaneRepo;
import com.vedasole.flight_service.service.service_interface.AirplaneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepo airplaneRepo;
    private final ModelMapper modelMapper;
    private static final String AIRPLANE_STRING="Airplane";
    private static final String AIRPLANE_ID_STRING="airplaneId";

    @Override
    @Transactional
    public AirplaneDto createAirplane(AirplaneDto airplaneDto) {
        return convertToDto(airplaneRepo.save(dtoToAirplane(airplaneDto)));
    }

    @Override
    @Transactional
    public AirplaneDto updateAirplane(String airplaneId, AirplaneDto airplaneDto) {
        return convertToDto(airplaneRepo.findById(airplaneId).map(
                airplane -> {
                    airplaneDto.setAirplaneId(airplaneId);
                    airplaneDto.setCreatedDate(airplane.getCreatedDate());
                    modelMapper.map(airplaneDto, airplane);
                    return airplaneRepo.save(airplane);
                }
        ).orElseThrow(() -> new ResourceNotFoundException(AIRPLANE_STRING, AIRPLANE_ID_STRING, airplaneId)));
    }

    @Override
    @Transactional
    public AirplaneDto deleteAirplane(String airplaneId) {
        return airplaneRepo.findById(airplaneId).map(
                airplane -> {
                    airplaneRepo.delete(airplane);
                    return convertToDto(airplane);
                }
        ).orElseThrow(() -> new ResourceNotFoundException(AIRPLANE_STRING, AIRPLANE_ID_STRING, airplaneId));
    }

    @Override
    @Transactional(readOnly = true)
    public AirplaneDto getAirplaneById(String airplaneId) {
        return convertToDto(airplaneRepo.findById(airplaneId)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPLANE_STRING, AIRPLANE_ID_STRING, airplaneId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AirplaneDto> getAllAirplanes(int page, int size, String sortBy, String order) {
        return airplaneRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    private AirplaneDto convertToDto(Airplane airplane) {
        if (airplane == null) return null;
        return modelMapper.map(airplane, AirplaneDto.class);
    }

    private Airplane dtoToAirplane(AirplaneDto airplaneDto) {
        if (airplaneDto == null) return null;
        return modelMapper.map(airplaneDto, Airplane.class);
    }

}