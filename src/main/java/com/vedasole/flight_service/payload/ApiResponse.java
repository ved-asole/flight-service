package com.vedasole.flight_service.payload;

public record ApiResponse(String message, Object data, boolean success) {}