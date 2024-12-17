package com.fiap.springdatamongodb.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Permite que nossa classe faça a recomendação de como tratar as exception
@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException exception) {
    // Verifico qual os campos com erros e atribuo a mensagem
    List<String> errors = exception.getBindingResult().getFieldErrors()
        .stream().map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

}
