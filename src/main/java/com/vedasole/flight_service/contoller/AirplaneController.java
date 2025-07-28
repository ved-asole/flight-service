package com.vedasole.flight_service.contoller;

import com.vedasole.flight_service.payload.AirplaneDto;
import com.vedasole.flight_service.service.service_interface.AirplaneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/airplanes")
@AllArgsConstructor
public class AirplaneController {

    private final AirplaneService airplaneService;

    @PostMapping
    public ResponseEntity<AirplaneDto> createAirplane(@RequestBody @Valid AirplaneDto airplaneDto) {
        AirplaneDto savedAirplaneDto = airplaneService.createAirplane(airplaneDto);
        return new ResponseEntity<>(
                savedAirplaneDto,
                CREATED
        );
    }

    @PutMapping("{airplaneId}")
    public ResponseEntity<AirplaneDto> updateAirplane(@PathVariable String airplaneId, @RequestBody @Valid AirplaneDto airplaneDto) {
        AirplaneDto updatedAirplaneDto = airplaneService.updateAirplane(airplaneId, airplaneDto);
        return ResponseEntity.ok(updatedAirplaneDto);
    }

    @DeleteMapping("{airplaneId}")
    public ResponseEntity<AirplaneDto> deleteAirplane(@PathVariable String airplaneId) {
        AirplaneDto airplaneDto = airplaneService.deleteAirplane(airplaneId);
        return ResponseEntity.ok(airplaneDto);
    }

    @GetMapping("{airplaneId}")
    public ResponseEntity<AirplaneDto> getAirplaneById(@PathVariable String airplaneId) {
        AirplaneDto airplaneDto = airplaneService.getAirplaneById(airplaneId);
        return ResponseEntity.ok(airplaneDto);
    }

    @GetMapping
    public ResponseEntity<Page<AirplaneDto>> getAllAirplanes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "airlineIataCode") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return new ResponseEntity<>(
                airplaneService.getAllAirplanes(page, size, sortBy, order),
                HttpStatus.OK
        );
    }

}