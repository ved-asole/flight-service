package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Country;
import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.repository.CountryRepo;
import com.vedasole.flight_service.service.service_interface.CountryService;
import jakarta.persistence.EntityNotFoundException;
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
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;
    private final ModelMapper modelMapper;
    private static final String COUNTRY_NOT_FOUND = "Country not found with id : ";

    @Override
    @Transactional
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = convertToEntity(countryDto);
        return convertToDto(countryRepo.save(country));
    }

    @Override
    @Transactional
    public CountryDto updateCountry(String countryId, CountryDto countryDto) {
        Country country = countryRepo.findById(countryId).orElseThrow(
                () -> new EntityNotFoundException(COUNTRY_NOT_FOUND + countryId)
        );
        modelMapper.map(countryDto, country);
        return convertToDto(countryRepo.save(country));
    }

    @Override
    @Transactional
    public CountryDto deleteCountry(String countryId) {
        return countryRepo.findById(countryId).map(country -> {
            countryRepo.delete(country);
            return convertToDto(country);
        }).orElseThrow(
                () -> new EntityNotFoundException(COUNTRY_NOT_FOUND + countryId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDto getCountryById(String countryId) {
        return countryRepo.findById(countryId).map(this::convertToDto).orElseThrow(
                () -> new EntityNotFoundException(COUNTRY_NOT_FOUND + countryId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDto> getAllCountries(int page, int size, String sortBy, String order) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        return countryRepo.findAll(pageRequest).map(this::convertToDto);
    }

    public CountryDto convertToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    public Country convertToEntity(CountryDto countryDto) {
        return modelMapper.map(countryDto, Country.class);
    }

}
