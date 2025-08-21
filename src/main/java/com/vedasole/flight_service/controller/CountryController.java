package com.vedasole.flight_service.controller;

import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.service.service_interface.CountryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/countries")
@AllArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    public ResponseEntity<CountryDto> addCountry(@Valid @RequestBody CountryDto countryDto) {
        CountryDto savedCountryDto = countryService.addCountry(countryDto);
        return new ResponseEntity<>(savedCountryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable String countryId,
            @Valid @RequestBody CountryDto countryDto
    ) {
        CountryDto updatedCountryDto = countryService.updateCountry(countryId, countryDto);
        return new ResponseEntity<>(updatedCountryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<CountryDto> deleteCountry(@PathVariable String countryId) {
        CountryDto deletedCountryDto = countryService.deleteCountry(countryId);
        return new ResponseEntity<>(deletedCountryDto, HttpStatus.OK);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable String countryId) {
        CountryDto countryDto = countryService.getCountryById(countryId);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CountryDto>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "countryName") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return new ResponseEntity<>(countryService.getAllCountries(page, size, sortBy, order), HttpStatus.OK);
    }

}