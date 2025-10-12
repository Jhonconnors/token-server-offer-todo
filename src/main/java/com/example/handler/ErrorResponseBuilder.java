package com.example.handler;

import com.example.contract.model.ErrorResponse;
import com.example.contract.model.ErrorResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ErrorResponseBuilder {

    public static ResponseEntity<ErrorResponse> build(HttpStatus status, String code, String message) {
        ErrorResponseData data = new ErrorResponseData()
                .severity(ErrorResponseData.SeverityEnum.ERROR)
                .code(code)
                .message(message);

        ErrorResponse response = new ErrorResponse().data(data);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ErrorResponse> fromValidationError(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder("Validation failed: ");

        // Recorremos solo los errores de campo (FieldError)
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String cleanMessage = String.format(
                    "Field error in object '%s' on field '%s': rejected value",
                    fieldError.getObjectName(),
                    fieldError.getField()
            );

            message.append(cleanMessage).append("; ");
        }

        ErrorResponseData data = new ErrorResponseData()
                .severity(ErrorResponseData.SeverityEnum.ERROR)
                .code("INVALID_REQUEST")
                .message(message.toString().trim());

        ErrorResponse response = new ErrorResponse().data(data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}