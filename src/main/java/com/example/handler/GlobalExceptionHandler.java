package com.example.handler;

import com.example.contract.model.ErrorResponse;
import com.example.exception.LoginFailedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return ErrorResponseBuilder.fromValidationError(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST",
                "Invalid request parameters"
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.FORBIDDEN,
                "INVALID_CLIENT_ID",
                "Error in client-id"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Unexpected server error"
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundResourceException(Exception ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.NOT_FOUND,
                "Error in URL",
                "Resource Not Exist"
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedMethodException(Exception ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.NOT_FOUND,
                "Error Method Http",
                "Method is not Supported"
        );
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> loginFailed(LoginFailedException ex) {
        return ErrorResponseBuilder.build(
                HttpStatus.UNAUTHORIZED,
                "Error With Credentials",
                ex.getMessage()
        );
    }
}