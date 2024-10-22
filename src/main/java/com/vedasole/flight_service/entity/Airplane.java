package com.vedasole.flight_service.entity;

import com.vedasole.flight_service.entity.enums.AirplaneType;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airplanes")
@CompoundIndex(name = "idx_iataType_type", def = "{'iataType': 1, 'airplaneType': 1}")
public class Airplane {

    @Id
    private String airplaneId;

    @Indexed(name = "idx_iataType")
    private String iataType;

    private String constructionNumber;
    private String airlineIataCode;
    private String iataCodeLong;
    private String iataCodeShort;
    private String airlineIcaoCode;
    private LocalDate deliveryDate;
    private Engine engines;
    private LocalDate firstFlightDate;
    private Model model;
    private String icaoCodeHex;
    private Registration registration;
    private Integer planeAge;
    private String planeClass; // Class of the airplane (e.g., passenger, cargo)
    private String planeOwner;
    private String planeSeries;
    private String planeStatus;
    private String productionLine;
    private int totalCapacity;
    private AirplaneType airplaneType;
    private Double pricePerKm;
    private SeatingConfiguration seatingConfiguration;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Transient
    private static final Random random = new SecureRandom();

    private static final int REGIONAL_MIN_CAPACITY = 40;
    private static final int REGIONAL_MAX_CAPACITY = 70;
    private static final int NARROW_BODY_MIN_CAPACITY = 100;
    private static final int NARROW_BODY_MAX_CAPACITY = 150;
    private static final int WIDE_BODY_MIN_CAPACITY = 200;
    private static final int WIDE_BODY_MAX_CAPACITY = 350;

    private static final double ECONOMY_BASE_PRICE = 100.0;
    private static final double BUSINESS_BASE_PRICE = 250.0;
    private static final double FIRST_CLASS_BASE_PRICE = 400.0;


    @PostConstruct
    private void init() {
        initializeAirplane();
    }

    // Method to initialize the airplane with random values
    public void initializeAirplane() {
        if (productionLine!=null) {
            this.airplaneType = productionLine.contains("Boeing") || productionLine.contains("Airbus")
                    || productionLine.contains("Douglas") || productionLine.contains("Embraer") ?
                    AirplaneType.WIDE_BODY : AirplaneType.getRandomType();
        } else this.airplaneType = AirplaneType.getRandomType();
        this.totalCapacity = calculateCapacity();
        this.seatingConfiguration = initializeSeatingConfiguration();
        this.pricePerKm = calculatePricePerKm();
    }

    // Method to calculate totalCapacity based on aircraft type
    private int calculateCapacity() {
        return switch (airplaneType.getDescription()) {
            case "Regional" ->
                // 40-70 seats
                    random.nextInt(REGIONAL_MAX_CAPACITY - REGIONAL_MIN_CAPACITY + 1) + REGIONAL_MIN_CAPACITY;
            case "Narrow_Body" ->
                // 100-150 seats
                    random.nextInt(NARROW_BODY_MAX_CAPACITY - NARROW_BODY_MIN_CAPACITY + 1) + NARROW_BODY_MIN_CAPACITY;
            case "Wide_Body" ->
                // 200-350 seats
                    random.nextInt(WIDE_BODY_MAX_CAPACITY - WIDE_BODY_MIN_CAPACITY + 1) + WIDE_BODY_MIN_CAPACITY;
            default -> 0; // Unknown aircraft type
        };
    }

    // Method to generate random seating totalCapacity
    private SeatingConfiguration initializeSeatingConfiguration() {
        // Percentage distribution for seating classes
        final double economySeatPercentage = 0.70; // 70% for Economy
        final double businessSeatPercentage = 0.20; // 20% for Business
        final double firstClassSeatPercentage = 0.10; // 10% for First Class

        int economyClassCapacity = (int) (totalCapacity * economySeatPercentage);
        int businessClassCapacity = (int) (totalCapacity * businessSeatPercentage);
        int firstClassCapacity = (int) (totalCapacity * firstClassSeatPercentage);

        return new SeatingConfiguration(
                economyClassCapacity, businessClassCapacity, firstClassCapacity,
                ECONOMY_BASE_PRICE, BUSINESS_BASE_PRICE, FIRST_CLASS_BASE_PRICE
        );
    }

    // Method to calculate price per km based on airplane aircraftType
    private double calculatePricePerKm() {
        return random.nextDouble() * (3.0 - 1.0) + 1.0; // Random price between 1 and 3
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatingConfiguration {
        private int economyClassCapacity;
        private int businessClassCapacity;
        private int firstClassCapacity;

        // Base prices for each class
        private Double economyBasePrice;
        private Double businessBasePrice;
        private Double firstClassBasePrice;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Engine {
        private Integer count;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model {
        private String code;
        private String name;
        private String series;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Registration {
        private String number;
        private LocalDate date;
        private String testRegistrationNumber;
    }

}