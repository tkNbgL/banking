package com.eteration.simplebanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionTypeNotValidException.class)
    public ResponseEntity<String> transactionTypeNotValidException(TransactionTypeNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
