package com.vedasole.flight_service.contoller;

import com.vedasole.flight_service.payload.AirportDto;
import com.vedasole.flight_service.service.service_interface.AirportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/airports")
@AllArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportDto> addAirport(@Valid @RequestBody AirportDto airportDto) {
        AirportDto savedAirportDto = airportService.addAirport(airportDto);
        return new ResponseEntity<>(savedAirportDto, HttpStatus.CREATED);
    }

    @PutMapping("/{airportId}")
    public ResponseEntity<AirportDto> updateAirport(
            @PathVariable String airportId,
            @Valid @RequestBody AirportDto airportDto
    ) {
        AirportDto updatedAirportDto = airportService.updateAirport(airportId, airportDto);
        return new ResponseEntity<>(updatedAirportDto, HttpStatus.OK);
    }

    @DeleteMapping("/{airportId}")
    public ResponseEntity<AirportDto> deleteAirport(@PathVariable String airportId) {
        AirportDto deletedAirportDto = airportService.deleteAirport(airportId);
        return new ResponseEntity<>(deletedAirportDto, HttpStatus.OK);
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<AirportDto> getAirportById(@PathVariable String airportId) {
        AirportDto airportDto = airportService.getAirportById(airportId);
        return new ResponseEntity<>(airportDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<AirportDto>> getAllAirports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "airportName") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return new ResponseEntity<>(airportService.getAllAirports(page, size, sortBy, order), HttpStatus.OK);
    }

}