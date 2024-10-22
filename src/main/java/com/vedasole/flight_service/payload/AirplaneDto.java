package com.vedasole.flight_service.payload;

import com.vedasole.flight_service.entity.enums.AirplaneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {

    private String airplaneId;
    private String iataType;
    private String constructionNumber;
    private String airlineIataCode;
    private String iataCodeLong;
    private String iataCodeShort;
    private String airlineIcaoCode;
    private LocalDate deliveryDate;
    private EngineDto engines;
    private LocalDate firstFlightDate;
    private Model model;
    private String icaoCodeHex;
    private RegistrationDto registration;
    private Integer planeAge;
    private String planeClass; // Class of the airplane (e.g., passenger, cargo)
    private String planeOwner;
    private String planeSeries;
    private String planeStatus;
    private String productionLine;
    private int totalCapacity;
    private AirplaneType aircraftType;
    private Double pricePerKm;
    private SeatingConfigurationDto seatingConfiguration;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatingConfigurationDto implements Serializable {
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
    public static class EngineDto implements Serializable {
        private Integer count;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model implements Serializable {
        private String code;
        private String name;
        private String series;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistrationDto implements Serializable {
        private String number;
        private LocalDate date;
        private String testRegistrationNumber;
    }

}