package com.vedasole.flight_service.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto implements Serializable {

    @NotNull(message = "Country ID cannot be null")
    private String countryId;

    @NotNull(message = "Capital cannot be null")
    @Size(min = 2, max = 100, message = "Capital name must be between 2 and 100 characters")
    private String capital;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    private String currencyCode;

    @Size(max = 5, message = "FIPS code must be at most 5 characters")
    private String fipsCode;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid ISO 3 code format")
    private String countryIso3;

    @NotNull(message = "Continent cannot be null")
    private String continent;

    @NotNull(message = "Country name cannot be null")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    private String countryName;

    @NotNull(message = "Currency name cannot be null")
    private String currencyName;

    @NotNull(message = "Country ISO Numeric cannot be null")
    @Positive(message = "Country ISO Numeric must be positive")
    private Integer countryIsoNumeric;

    @Size(max = 5, message = "Phone prefix must be at most 5 characters")
    private String phonePrefix;

    @NotNull
    @Positive(message = "Population should be a valid number")
    private Long population;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}