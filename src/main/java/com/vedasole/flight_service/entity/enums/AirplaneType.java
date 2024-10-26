package com.vedasole.flight_service.entity.enums;

import lombok.Getter;

import java.security.SecureRandom;
import java.util.Random;

@Getter
public enum AirplaneType {

    NARROW_BODY(1, "Narrow_Body"),
    WIDE_BODY(2, "Wide_Body"),
    REGIONAL(3, "Regional"),
    CARGO(4, "Cargo");

    private final int id;
    private final String description;

    AirplaneType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private static final Random random = new SecureRandom();

    public static AirplaneType getRandomType() {
        return AirplaneType.values()[random.nextInt(AirplaneType.values().length)];
    }

}