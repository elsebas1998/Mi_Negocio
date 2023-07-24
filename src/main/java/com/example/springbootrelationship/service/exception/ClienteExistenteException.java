package com.example.springbootrelationship.service.exception;

public class ClienteExistenteException extends RuntimeException {

    public ClienteExistenteException(String message) {
        super(message);
    }
}
