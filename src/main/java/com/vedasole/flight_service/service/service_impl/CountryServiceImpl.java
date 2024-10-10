package com.vedasole.flight_service.service.service_impl;

import com.vedasole.flight_service.entity.Country;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.repository.CountryRepo;
import com.vedasole.flight_service.service.service_interface.CountryService;
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
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;
    private final ModelMapper modelMapper;
    private static final String COUNTRY_STRING = "Country";

    @Override
    @Transactional
    @CacheEvict(value = "countries", allEntries = true)
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = convertToEntity(countryDto);
        checkExistsWithIdCountryNameOrIso2(country);
        return convertToDto(countryRepo.save(country));
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "countries", key = "#countryId"),
                    @CacheEvict(value = "countries", allEntries = true)
            },
            cacheable = {@Cacheable(value = "countries", key = "#countryId")}
    )
    public CountryDto updateCountry(String countryId, CountryDto countryDto) {
        Country updatedCountry = countryRepo.findById(countryId)
                .map(country -> {
                    countryDto.setCountryId(countryId);
                    countryDto.setCreatedDate(country.getCreatedDate());
                    modelMapper.map(countryDto, country);
                    checkExistsWithCountryNameOrIso2ExceptCurrent(country);
                    return countryRepo.save(country);
                }).orElseThrow(() -> new ResourceNotFoundException(COUNTRY_STRING, "id", countryId)
        );
        return convertToDto(updatedCountry);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "countries", key = "#countryId"),
            @CacheEvict(value = "countries", allEntries = true)
    })
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
    @Cacheable(value = "countries", key = "#countryId")
    public CountryDto getCountryById(String countryId) {
        return countryRepo.findById(countryId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(COUNTRY_STRING, "id", countryId));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "countries",
            key = "'all-page:' + #page + 'size:' + #size + 'sortBy:' + #sortBy + 'order:' + #order",
            condition = "#page >= 0 and #page <= 10 and #size >= 0 and #size <= 100"
    )
    public Page<CountryDto> getAllCountries(int page, int size, String sortBy, String order) {
        return countryRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy)))
                .map(this::convertToDto);
    }

    private void checkExistsWithIdCountryNameOrIso2(Country country) {
        if (countryRepo.existsByCountryName(country.getCountryName())) {
            log.error("Country name already exists: {}", country.getCountryName());
            throw new DataIntegrityViolationException("Country name already exists: " + country.getCountryName());
        }
        if (countryRepo.existsByCountryIso2(country.getCountryIso2())) {
            log.error("Country ISO2 already exists: {}", country.getCountryIso2());
            throw new DataIntegrityViolationException("Country ISO2 already exists: " + country.getCountryIso2());
        }
    }

    private void checkExistsWithCountryNameOrIso2ExceptCurrent(Country country) {
        if (countryRepo.countByCountryNameAndCountryIdNot(country.getCountryName(), country.getCountryId()) > 0) {
            log.error("Country name already exists: {}", country.getCountryName());
            throw new DataIntegrityViolationException("Country name already exists: " + country.getCountryName());
        }
        if (countryRepo.countByCountryIso2AndCountryIdNot(country.getCountryIso2(), country.getCountryId()) > 0) {
            log.error("Country ISO2 already exists: {}", country.getCountryIso2());
            throw new DataIntegrityViolationException("Country ISO2 already exists: " + country.getCountryIso2());
        }
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
