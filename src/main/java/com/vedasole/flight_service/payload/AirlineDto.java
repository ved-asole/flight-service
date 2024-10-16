package com.vedasole.flight_service.payload;

import com.vedasole.flight_service.entity.Airline;
import com.vedasole.flight_service.entity.enums.AirlineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDto implements Serializable {

    private String airlineId;

    @NotNull(message = "Callsign cannot be null")
    private String callsign;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "IATA Code cannot be null")
    @Pattern(regexp = "[A-Z]{2}", message = "IATA Code must be two uppercase letters")
    private String iataCode;

    @NotNull(message = "ICAO Code cannot be null")
    @Pattern(regexp = "[A-Z]{3}", message = "ICAO Code must be three uppercase letters")
    private String icaoCode;

    private Airline.Country country;
    private Airline.Fleet fleet;
    private String hubCode;
    private Integer founded;

    @NotNull(message = "Status cannot be null")
    @Pattern(regexp = "active|inactive", message = "Status must be either 'active' or 'inactive'")
    private String status;

    private Set<AirlineType> type;

    private Double airlinePremium;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Country implements Serializable {
        private String iso2;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fleet implements Serializable {
        private Double averageAge;
        private Integer size;
    }

}