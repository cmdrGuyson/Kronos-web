package com.guyson.kronos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class APIException {
    private String message;
    private HttpStatus status;

    public APIException(String message) {
        this.message = message;
    }
}
