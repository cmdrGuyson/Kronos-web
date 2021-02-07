package com.guyson.kronos.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class SimpleMessageDto {

    private String message;
    private HttpStatus type;

}
