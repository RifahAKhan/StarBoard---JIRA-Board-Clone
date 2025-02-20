package com.clone.jiraclone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<String> handleProjectAlreadyExistsException(ProjectAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectIdAndNameNotEditableException.class)
    public ResponseEntity<String> handleProjectIdAndNameNotEditableException(ProjectIdAndNameNotEditableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}