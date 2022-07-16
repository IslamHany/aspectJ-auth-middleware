package com.example.demo.exceptions;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String userId) {
        super(String.format("User with Id: %s , is not authorized", userId));
    }
}
