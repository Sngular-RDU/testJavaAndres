package com.example.prueba.tecnica.pruebaTecnica.api;

import javax.management.InstanceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMapper {

  public ResponseEntity<String> exceptionToApiError(Exception ex) {
    if (ex instanceof InstanceNotFoundException) {
      return new ResponseEntity<>(" No se ha encontrado una tarifa aplicable al producto", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
