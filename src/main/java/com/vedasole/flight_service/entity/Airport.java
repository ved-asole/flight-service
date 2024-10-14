package com.vedasole.flight_service.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("airports")
public class Airport {

    @Id
    private String airportId;

    @Indexed
    @NotNull
    private String airportName;

    @Indexed(unique = true)
    @Size(min = 3, max = 3)
    private String iataCode;

    @Indexed
    @Size(min = 4, max = 4)
    private String icaoCode;

    @Indexed
    private String cityIataCode;

    @Indexed
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code")
    private String countryIso2;

    private String countryName;

    @Digits(integer = 10, fraction = 0)
    private Long geonameId;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    private Float gmt;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Pattern(regexp = "^[A-Za-z_]+/[A-Za-z_]+$", message = "Invalid timezone format")
    private String timezone;

    private String phoneNumber;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}