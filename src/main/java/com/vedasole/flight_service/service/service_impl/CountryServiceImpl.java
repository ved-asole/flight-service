package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Country;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.repository.CountryRepo;
import com.vedasole.flight_service.service.service_interface.CountryService;
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
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;
    private final ModelMapper modelMapper;
    private static final String COUNTRY_STRING = "Country";

    @Override
    @Transactional
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = convertToEntity(countryDto);
        checkExistsWithCountryNameOrIso2(country);
        return convertToDto(countryRepo.save(country));
    }

    @Override
    @Transactional
    public CountryDto updateCountry(String countryId, CountryDto countryDto) {
        Country updatedCountry = countryRepo.findById(countryId)
                .map(country -> {
                    countryDto.setCountryId(countryId);
                    countryDto.setCreatedDate(country.getCreatedDate());
                    modelMapper.map(countryDto, country);
                    checkExistsWithCountryNameOrIso2(country);
                    return countryRepo.save(country);
                }).orElseThrow(() -> new ResourceNotFoundException(COUNTRY_STRING, "id", countryId)
        );
        return convertToDto(updatedCountry);
    }

    @Override
    @Transactional
    public CountryDto deleteCountry(String countryId) {
        return countryRepo.findById(countryId).map(country -> {
            countryRepo.delete(country);
            return convertToDto(country);
        }).orElseThrow(
                () -> new ResourceNotFoundException(COUNTRY_STRING, "id", countryId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDto getCountryById(String countryId) {
        return countryRepo.findById(countryId).map(this::convertToDto).orElseThrow(
                () -> new ResourceNotFoundException(COUNTRY_STRING, "id", countryId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDto> getAllCountries(int page, int size, String sortBy, String order) {
        return countryRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    private void checkExistsWithCountryNameOrIso2(Country country) {
        boolean existsByCountryName = countryRepo.existsByCountryName(country.getCountryName());
        if (existsByCountryName) throw new DataIntegrityViolationException("Country with countryName " + country.getCountryName() + " already exists");
        boolean existsByCountryIso2 = countryRepo.existsByCountryIso2(country.getCountryIso2());
        if (existsByCountryIso2) throw new DataIntegrityViolationException("Country with countryIso2 " + country.getCountryIso2() + " already exists");
    }

    public CountryDto convertToDto(Country country) {
        if (country == null) return null;
        return modelMapper.map(country, CountryDto.class);
    }

    public Country convertToEntity(CountryDto countryDto) {
        if (countryDto == null) return null;
        return modelMapper.map(countryDto, Country.class);
    }

}
