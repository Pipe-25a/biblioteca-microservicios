package com.biblioteca.reserva_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarError(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}