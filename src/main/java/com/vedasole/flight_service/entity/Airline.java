package com.vedasole.flight_service.entity;

import com.vedasole.flight_service.entity.enums.AirlineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airlines")
@CompoundIndex(name = "idx_name_iata", def = "{'name': 1, 'icaoCode': 1}")
public class Airline {

    @Id
    private String airlineId;

    @Indexed(name = "idx_callsign")
    @NotNull(message = "Callsign cannot be null")
    private String callsign;

    @Indexed(name = "idx_name")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "IATA Code cannot be null")
    @Pattern(regexp = "[A-Z]{2}", message = "IATA Code must be two uppercase letters")
    private String iataCode;

    @NotNull(message = "ICAO Code cannot be null")
    @Pattern(regexp = "[A-Z]{3}", message = "ICAO Code must be three uppercase letters")
    private String icaoCode;

    private Country country;
    private Fleet fleet;
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

    @Transient
    private static final Random random = new SecureRandom();

    public void calculateAirlinePremium() {
        double minPremium = 5.0; // Minimum percentage
        double maxPremium = 20.0; // Maximum percentage (20%)
        this.airlinePremium = minPremium + (maxPremium - minPremium) * random.nextDouble();
    }

    // Nested classes for embedded documents

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