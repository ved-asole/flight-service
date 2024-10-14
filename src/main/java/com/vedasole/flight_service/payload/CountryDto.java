package com.vedasole.flight_service.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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

    private String countryId;

    @NotNull
    private String capital;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    private String currencyCode;

    private String fipsCode;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid ISO 3 code format")
    private String countryIso3;

    @NotNull
    private String continent;

    @NotNull
    private String countryName;

    @NotNull
    private String currencyName;

    @NotNull
    private Integer countryIsoNumeric;

    private String phonePrefix;

    @NotNull
    @Positive(message = "Population should be a valid number")
    private Long population;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
