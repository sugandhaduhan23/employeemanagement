package com.example.employeemanagement.controller.advice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.employeemanagement.dto.MessageDetails;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.exceptions.InsufficientLeaveBalanceException;
import com.example.employeemanagement.exceptions.InvalidCredentialException;
import com.example.employeemanagement.exceptions.ProjectMappedException;
import com.example.employeemanagement.exceptions.ProjectNotFoundException;

@RestControllerAdvice
public class ExceptionControlAdvice {

    @ExceptionHandler(InsufficientLeaveBalanceException.class)
    public ResponseEntity<MessageDetails> insufficientLeavesException(InsufficientLeaveBalanceException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<MessageDetails> invalidCredentialException(InvalidCredentialException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<MessageDetails> employeeNotFound(EmployeeNotFoundException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<MessageDetails> projectNotFound(ProjectNotFoundException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(ProjectMappedException.class)
    public ResponseEntity<MessageDetails> projectMappedException(ProjectMappedException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDetails> handleRuntimeException(RuntimeException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage());
        return ResponseEntity.internalServerError().body(msg);
    }
}