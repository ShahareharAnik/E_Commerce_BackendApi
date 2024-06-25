package com.anik.E_Commerce.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class GenericResponse {
    private Integer id;
    private String message;
    private Integer statusCode;
    private LocalDateTime timestamp;


    public GenericResponse( String message, HttpStatus httpStatus ) {
        this.message = message;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public GenericResponse( Integer id, String message, HttpStatus httpStatus ) {
        this.id = id;
        this.message = message;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }
}
