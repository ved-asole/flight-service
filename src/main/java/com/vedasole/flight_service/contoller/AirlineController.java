package com.vedasole.flight_service.contoller;

import com.vedasole.flight_service.payload.AirlineDto;
import com.vedasole.flight_service.service.service_interface.AirlineService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/airlines")
@AllArgsConstructor
public class AirlineController {
    
    private AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineDto> createAirline(@Valid @RequestBody AirlineDto airlineDto) {
        return new ResponseEntity<>(
                airlineService.createAirline(airlineDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{airlineId}")
    public ResponseEntity<AirlineDto> updateAirline(
            @PathVariable String airlineId,
            @Valid @RequestBody AirlineDto airlineDto
    ) {
        return new ResponseEntity<>(
                airlineService.updateAirline(airlineId, airlineDto),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{airlineId}")
    public ResponseEntity<AirlineDto> deleteAirline(@PathVariable String airlineId) {
        return new ResponseEntity<>(
                airlineService.deleteAirline(airlineId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{airlineId}")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable String airlineId) {
        return new ResponseEntity<>(
                airlineService.getAirlineById(airlineId),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<Page<AirlineDto>> getAllAirlines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return new ResponseEntity<>(
            airlineService.getAllAirlines(page, size, sortBy, order),
            HttpStatus.OK);
    }

}