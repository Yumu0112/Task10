package com.example.customerdb.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(int id) {
        super("ID:" + id + " was not found");
    }
}
