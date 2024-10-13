package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.City;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.CityDto;
import com.vedasole.flight_service.repository.CityRepo;
import com.vedasole.flight_service.repository.CountryRepo;
import com.vedasole.flight_service.service.service_interface.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;
    private final ModelMapper modelMapper;
    private static final String CITY_STRING = "City";

    @Override
    @Transactional
    public CityDto addCity(CityDto cityDto) {
        if (cityRepo.existsByIataCode(cityDto.getIataCode()))
            throw new ResourceNotFoundException(CITY_STRING, "IATA code", cityDto.getIataCode());
        if (!countryRepo.existsByCountryIso2(cityDto.getCountryIso2()))
            throw new DataIntegrityViolationException("Country with ISO2 does not exist: " + cityDto.getCountryIso2());
        return convertToDto(cityRepo.save(convertToEntity(cityDto)));
    }

    @Override
    @Transactional
    public CityDto updateCity(String cityId, CityDto cityDto) {
        City updatedCity = cityRepo.findById(cityId).map(city -> {
//            To prevent updating createdDate
            cityDto.setCityId(cityId);
            cityDto.setCreatedDate(city.getCreatedDate());
            modelMapper.map(cityDto, city);
            // Check if the new IATA code already exists in another city
            if (cityRepo.existsByIataCodeAndCityIdNot(city.getIataCode(), cityId))
                throw new ResourceNotFoundException(CITY_STRING, "IATA code", city.getIataCode());
            if (countryRepo.existsByCountryIso2(city.getCountryIso2()))
                throw new DataIntegrityViolationException("Country with ISO2 code already exists: " + cityDto.getCountryIso2());
            return cityRepo.save(city);
        }).orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
        return convertToDto(updatedCity);
    }

    @Override
    @Transactional
    public CityDto deleteCity(String cityId) {
        return cityRepo.findById(cityId).map(city -> {
            cityRepo.delete(city);
            return convertToDto(city);
        }).orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
    }

    @Override
    @Transactional(readOnly = true)
    public CityDto getCityById(String cityId) {
        return cityRepo.findById(cityId).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDto> getAllCities(int page, int size, String sortBy, String order) {
        return cityRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    public CityDto convertToDto(City city) {
        if (city == null) return null;
        return modelMapper.map(city, CityDto.class);
    }

    public City convertToEntity(CityDto cityDto) {
        if (cityDto == null) return null;
        return modelMapper.map(cityDto, City.class);
    }

}