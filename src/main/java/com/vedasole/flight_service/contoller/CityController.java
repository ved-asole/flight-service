package com.vedasole.flight_service.contoller;

import com.vedasole.flight_service.payload.CityDto;
import com.vedasole.flight_service.service.service_interface.CityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityDto> addCountry(@Valid @RequestBody CityDto cityDto) {
        CityDto savedCityDto = cityService.addCity(cityDto);
        return new ResponseEntity<>(savedCityDto, HttpStatus.CREATED);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<CityDto> updateCountry(
            @PathVariable String cityId,
            @Valid @RequestBody CityDto cityDto
    ) {
        CityDto updatedCityDto = cityService.updateCity(cityId, cityDto);
        return new ResponseEntity<>(updatedCityDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<CityDto> deleteCountry(@PathVariable String cityId) {
        CityDto deletedCityDto = cityService.deleteCity(cityId);
        return new ResponseEntity<>(deletedCityDto, HttpStatus.OK);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityDto> getCountryById(@PathVariable String cityId) {
        CityDto cityDto = cityService.getCityById(cityId);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CityDto>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cityName") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return new ResponseEntity<>(cityService.getAllCities(page, size, sortBy, order), HttpStatus.OK);
    }

}