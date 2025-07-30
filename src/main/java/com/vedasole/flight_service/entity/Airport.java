package com.vedasole.flight_service.entity;

import jakarta.validation.constraints.Pattern;
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
    private String airportName;

    @Indexed(unique = true)
    private String iataCode;

    @Indexed
    private String icaoCode;

    @Indexed
    private String cityIataCode;

    @Indexed
    private String countryIso2;

    private String countryName;

    private Long geonameId;

    private Float gmt;

    private Double latitude;

    private Double longitude;

    @Pattern(regexp = "^[A-Za-z_]+/[A-Za-z_]+$", message = "Invalid timezone format")
    private String timezone;

    private String phoneNumber;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}