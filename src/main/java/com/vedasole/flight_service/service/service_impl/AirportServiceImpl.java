package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Airport;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.AirportDto;
import com.vedasole.flight_service.repository.AirportRepo;
import com.vedasole.flight_service.service.service_interface.AirportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepo airportRepo;
    private final ModelMapper modelMapper;
    private static final String AIRPORT_STRING = "Airport";

    @Override
    @Transactional
    @CacheEvict(value = "airports", allEntries = true)
    public AirportDto addAirport(AirportDto airportDto) {
        Airport airport = convertToEntity(airportDto);
        checkExistsWithIdAirportNameOrIata(airport);
        return convertToDto(airportRepo.save(airport));
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "airports", key = "#airportId"),
                    @CacheEvict(value = "airports", allEntries = true)
            },
            cacheable = @Cacheable(value = "airports", key = "#airportId")
    )
    public AirportDto updateAirport(String airportId, AirportDto airportDto) {
        Airport updatedAirport = airportRepo.findById(airportId)
                .map(airport -> {
                    airportDto.setAirportId(airportId);
                    airportDto.setCreatedDate(airport.getCreatedDate());
                    modelMapper.map(airportDto, airport);
                    checkExistsWithIdAirportNameOrIataExceptCurrent(airport);
                    return airportRepo.save(airport);
                })
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_STRING, "id", airportId));
        return convertToDto(updatedAirport);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "airports", key = "#airportId"),
                    @CacheEvict(value = "airports", allEntries = true)
            }
    )
    public AirportDto deleteAirport(String airportId) {
        return airportRepo.findById(airportId)
                .map(airport -> {
                    airportRepo.delete(airport);
                    return convertToDto(airport);
                })
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_STRING, "id", airportId));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "airports", key = "#airportId")
    public AirportDto getAirportById(String airportId) {
        return airportRepo.findById(airportId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_STRING, "id", airportId));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "airports",
            key = "'all-page:' + #page + 'size:' + #size + 'sortBy:' + #sortBy + 'order:' + #order",
            condition = "#page >= 0 and #page <= 10 and #size >= 0 and #size <= 100"
    )
    public Page<AirportDto> getAllAirports(int page, int size, String sortBy, String order) {
        return airportRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    private void checkExistsWithIdAirportNameOrIata(Airport airport) {
        if (airportRepo.existsByAirportName(airport.getAirportName())) {
            log.error("Airport name already exists: {}", airport.getAirportName());
            throw new DataIntegrityViolationException("Airport name already exists: " + airport.getAirportName());
        }
        if (airportRepo.existsByIataCode(airport.getIataCode())) {
            log.error("Airport IATA already exists: {}", airport.getIataCode());
            throw new DataIntegrityViolationException("Airport IATA already exists: " + airport.getIataCode());
        }
    }

    private void checkExistsWithIdAirportNameOrIataExceptCurrent(Airport airport) {
        if (airportRepo.countByAirportNameAndAirportIdNot(airport.getAirportName(), airport.getAirportId()) > 0) {
            log.error("Airport name already exists: {}", airport.getAirportName());
            throw new DataIntegrityViolationException("Airport name already exists: " + airport.getAirportName());
        }
        if (airportRepo.countByIataCodeAndAirportIdNot(airport.getIataCode(), airport.getAirportId()) > 0) {
            log.error("Airport IATA already exists: {}", airport.getIataCode());
            throw new DataIntegrityViolationException("Airport IATA already exists: " + airport.getIataCode());
        }
    }

    private AirportDto convertToDto(Airport airport) {
        if (airport == null) return null;
        return modelMapper.map(airport, AirportDto.class);
    }

    private Airport convertToEntity(AirportDto airportDto) {
        if (airportDto == null) return null;
        return modelMapper.map(airportDto, Airport.class);
    }

}
