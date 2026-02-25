package com.example.fooddelivery.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseEntity<RestError> handleException(Exception ex) {
        logger.error("Some error :(", ex);
        RestError re = new RestError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
    }
    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseBody
    public ResponseEntity<RestError> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.warn("Some error :(, cause: " + ex.getMessage());
        RestError re = new RestError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }
}