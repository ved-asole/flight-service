package com.vedasole.flight_service.payload;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto implements Serializable {

    private String airportId;

    @NotNull
    private String airportName;

    @Size(min = 3, max = 3)
    private String iataCode;

    @Size(min = 4, max = 4)
    private String icaoCode;

    private String cityIataCode;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code")
    private String countryIso2;

    private String countryName;

    @Digits(integer = 10, fraction = 0)
    private Long geonameId;

    @Digits(integer = 2, fraction = 2)
    private Float gmt;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Pattern(regexp = "^[A-Za-z_]+/[A-Za-z_]+$", message = "Invalid timezone format")
    private String timezone;

    private String phoneNumber;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}