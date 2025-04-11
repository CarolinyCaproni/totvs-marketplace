package com.totvs.totvs_marketplace_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.totvs.totvs_marketplace_backend.service.exception.RegraNegocioException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<String> handleRegraNegocioException(RegraNegocioException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
