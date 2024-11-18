package com.example.keikoshop2.exception;

public class ProductAlreadyExistsExeption extends RuntimeException {
    public ProductAlreadyExistsExeption(String message) {
        super(message);
    }
}
