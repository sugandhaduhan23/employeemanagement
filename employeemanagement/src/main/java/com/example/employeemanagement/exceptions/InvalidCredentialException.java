package com.example.employeemanagement.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(){
        super("Either username or password is incorrect!!!");
    }
}
