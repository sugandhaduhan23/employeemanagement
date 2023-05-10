package com.example.employeemanagement.exceptions;

public class InsufficientLeaveBalanceException extends RuntimeException{
    public InsufficientLeaveBalanceException(){
        super("Not enough leaves!!!");
    }
}
