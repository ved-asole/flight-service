package com.vedasole.flight_service.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {

    @NotNull(message = "City ID cannot be null")
    private String cityId;

    @Pattern(regexp = "^([+-]?\\d{1,2}(:\\d{2})?)$", message = "Invalid GMT format")
    private String gmt;

    @NotNull
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters long")
    private String iataCode;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @NotNull(message = "City name cannot be null")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String cityName;

    @NotNull(message = "Geoname ID cannot be null")
    private Long geonameId;

    @NotNull(message = "Latitude ID cannot be null")
    private Double latitude;

    @NotNull(message = "Longitude ID cannot be null")
    private Double longitude;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}