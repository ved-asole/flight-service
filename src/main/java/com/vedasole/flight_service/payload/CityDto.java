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

    private String cityId;

    private String gmt;

    @NotNull
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters long")
    private String iataCode;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @NotNull
    private String cityName;

    private Long geonameId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}