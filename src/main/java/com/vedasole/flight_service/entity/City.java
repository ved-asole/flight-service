package com.vedasole.flight_service.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cities")
public class City {

    @Id
    private Long cityId;

    private String gmt;

    @Indexed(unique = true)
    @NotNull
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters long")
    private String iataCode;

    @Indexed
    @NotNull
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code format")
    private String countryIso2;

    @Indexed
    @NotNull
    private String cityName;

    private String geonameId;

    private Double latitude;

    private Double longitude;

}
