package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Airline;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.AirlineDto;
import com.vedasole.flight_service.repository.AirlineRepo;
import com.vedasole.flight_service.service.service_interface.AirlineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AirlineServiceImpl implements AirlineService {

    private AirlineRepo airlineRepo;
    private ModelMapper modelMapper;
    private static final String AIRLINE_STRING = "Airline";
    private static final String AIRLINE_ID_STRING = "airlineId";

    @Override
    @Transactional
    @CacheEvict(value = "airlines", allEntries = true)
    public AirlineDto createAirline(AirlineDto airlineDto) {
        return airlineToDto(airlineRepo.save(dtoToAirline(airlineDto)));
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "airlines", key = "#airlineId"),
                    @CacheEvict(value = "airlines", allEntries = true)
            },
            cacheable = {@Cacheable(value = "airlines", key = "#airlineId")}
    )
    public AirlineDto updateAirline(String airlineId, AirlineDto airlineDto) {
        return airlineToDto(airlineRepo.findById(airlineId).map(airline -> {
            airlineDto.setAirlineId(airlineId);
            airlineDto.setCreatedDate(airline.getCreatedDate());
            modelMapper.map(airlineDto, airline);
            return airlineRepo.save(airline);
        }).orElseThrow(() -> new ResourceNotFoundException(AIRLINE_STRING, AIRLINE_ID_STRING, airlineId)));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "airlines", key = "#airlineId"),
            @CacheEvict(value = "airlines", allEntries = true)
    })
    public AirlineDto deleteAirline(String airlineId) {
        return airlineRepo.findById(airlineId).map(airline -> {
            airlineRepo.deleteById(airlineId);
            return airlineToDto(airline);
        }).orElseThrow(() -> new ResourceNotFoundException(AIRLINE_STRING, AIRLINE_ID_STRING, airlineId));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "airlines", key = "#airlineId")
    public AirlineDto getAirlineById(String airlineId) {
        return airlineToDto(airlineRepo.findById(airlineId)
                .orElseThrow(() -> new ResourceNotFoundException(AIRLINE_STRING, AIRLINE_ID_STRING, airlineId))
        );
    }

    @Override
    @Cacheable(
            value = "airlines",
            key = "'all-page:' + #page + 'size:' + #size + 'sortBy:' + #sortBy + 'order:' + #order",
            condition = "#page >= 0 and #page <= 10 and #size >= 0 and #size <= 100"
    )
    public Page<AirlineDto> getAllAirlines(int page, int size, String sortBy, String order) {
        return airlineRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy))
        ).map(this::airlineToDto);
    }

    private AirlineDto airlineToDto(Airline airline) {
        if (airline == null) return null;
        return modelMapper.map(airline, AirlineDto.class);
    }

    private Airline dtoToAirline(AirlineDto airlineDto) {
        if (airlineDto == null) return null;
        return modelMapper.map(airlineDto, Airline.class);
    }

}
