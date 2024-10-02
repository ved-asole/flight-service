package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.City;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.CityDto;
import com.vedasole.flight_service.repository.CityRepo;
import com.vedasole.flight_service.service.service_interface.CityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;
    private final ModelMapper modelMapper;
    private static final String CITY_STRING = "City";

    @Transactional
    @Override
    public CityDto addCity(CityDto cityDto) {
        return convertToDto(cityRepo.save(convertToEntity(cityDto)));
    }

    @Transactional
    @Override
    public CityDto updateCity(String cityId, CityDto cityDto) {
        City updatedCity = cityRepo.findById(cityId).map(city -> {
//            To prevent updating createdDate
            cityDto.setCreatedDate(city.getCreatedDate());
            modelMapper.map(cityDto, city);
            return cityRepo.save(city);
        }).orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
        return convertToDto(updatedCity);
    }

    @Transactional
    @Override
    public CityDto deleteCity(String cityId) {
        return cityRepo.findById(cityId).map(city -> {
            cityRepo.delete(city);
            return convertToDto(city);
        }).orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
    }

    @Transactional(readOnly = true)
    @Override
    public CityDto getCityById(String cityId) {
        return cityRepo.findById(cityId).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(CITY_STRING, "id", cityId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CityDto> getAllCities(int page, int size, String sortBy, String order) {
        return cityRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    public CityDto convertToDto(City city) {
        return modelMapper.map(city, CityDto.class);
    }

    public City convertToEntity(CityDto cityDto) {
        return modelMapper.map(cityDto, City.class);
    }

}