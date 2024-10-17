package com.vedasole.flight_service.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AirlineType {

    SCHEDULED(1, "Scheduled"),
    SUPPLIER(2, "Supplier"),
    CARGO(3, "Cargo"),
    CHARTER(4, "Charter"),
    VIRTUAL(5, "Virtual"),
    MANUFACTURER(6, "Manufacturer"),
    PRIVATE(7, "Private"),
    DIVISION(8, "Division"),
    GOVERNMENT(9, "Government"),
    OTHER(10, "Other");

    private final int id;
    private final String description;

}