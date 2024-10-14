package com.vedasole.flight_service.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Document(collection = "countries")
@CompoundIndex(name = "idx_country_iso2_name", def = "{'countryIso2': 1, 'countryName': 1}", unique = true)
public class Country {

    @Id
    private String countryId;

    private String capital;

    @Indexed(name = "idx_currency_code")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    private String currencyCode;

    private String fipsCode;

    @Indexed(name = "idx_country_iso2", unique = true)
    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid ISO 3 code format")
    private String countryIso3;

    private String continent;

    @Indexed(name = "idx_country_name", unique = true)
    @NotNull
    private String countryName;

    @NotNull
    private String currencyName;

    private Integer countryIsoNumeric;

    private String phonePrefix;

    @Positive(message = "Population should be a valid number")
    private Long population;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}