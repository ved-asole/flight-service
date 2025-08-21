package com.vedasole.flight_service.payload;

import com.vedasole.flight_service.entity.enums.AirplaneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDto {

    private String airplaneId;

    @NotNull(message = "IATA Type cannot be null")
    @Size(min = 3, max = 10, message = "IATA Type must be between 3 and 10 characters")
    private String iataType;

    @NotNull(message = "Construction Number cannot be null")
    private String constructionNumber;

    @NotNull(message = "Airline IATA Code cannot be null")
    @Size(min = 2, max = 2, message = "Airline IATA Code must be 2 characters")
    private String airlineIataCode;

    @Size(max = 10, message = "IATA Code Long must be at most 10 characters")
    private String iataCodeLong;

    @Size(max = 5, message = "IATA Code Short must be at most 5 characters")
    private String iataCodeShort;

    @Size(min = 3, max = 3, message = "Airline ICAO Code must be 3 characters")
    private String airlineIcaoCode;

    private LocalDate deliveryDate;
    private EngineDto engines;
    private LocalDate firstFlightDate;
    private Model model;

    @Pattern(regexp = "^[A-F0-9]{6}$", message = "ICAO Code Hex must be 6 hexadecimal characters")
    private String icaoCodeHex;
    private RegistrationDto registration;

    @Min(value = 0, message = "Plane age must be non-negative")
    private Integer planeAge;

    @NotNull(message = "Plane Class cannot be null")
    private String planeClass;
    private String planeOwner;
    private String planeSeries;

    @NotNull(message = "Plane Status cannot be null")
    private String planeStatus;
    private String productionLine;

    @Min(value = 1, message = "Total capacity must be at least 1")
    private int totalCapacity;

    @NotNull(message = "Airplane Type cannot be null")
    private AirplaneType airplaneType;

    @Min(value = 0, message = "Price per km must be non-negative")
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