package com.vedasole.flight_service.entity;

import com.vedasole.flight_service.entity.enums.AirlineType;
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
    private String callsign;

    @Indexed(name = "idx_name")
    private String name;

    private String iataCode;
    private String icaoCode;
    private Country country;
    private Fleet fleet;
    private String hubCode;
    private Integer founded;
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
    public static class Country {
        private String iso2;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fleet {
        private Double averageAge;
        private Integer size;
    }
}