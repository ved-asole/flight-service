package com.vedasole.flight_service.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private Object data;
    private String message;

    public ApiResponse(String message, boolean success) {
        this.success = success;
        this.message = message;
    }

}