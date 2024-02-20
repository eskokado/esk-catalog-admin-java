package com.eskcti.catalog.admin.infrastructure.api.controllers;


import com.eskcti.catalog.admin.domain.exceptions.DomainException;
import com.eskcti.catalog.admin.domain.exceptions.NotFoundException;
import com.eskcti.catalog.admin.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(
            final DomainException ex,
            final HttpServletRequest req,
            final HttpServletResponse res
    ) {
        return ResponseEntity.unprocessableEntity()
                .body(ApiError.from(ex));
    }
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(
            final DomainException ex,
            final HttpServletRequest req,
            final HttpServletResponse res
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.from(ex));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }
}
