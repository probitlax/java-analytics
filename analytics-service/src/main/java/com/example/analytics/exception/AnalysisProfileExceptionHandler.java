package com.example.analytics.exception;

import com.example.analytics.model.ErrorResponse;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class AnalysisProfileExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    ResponseEntity<ErrorResponse> profileNotFoundExceptionHandler(ProfileNotFoundException exception){
        final ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder()
                .applicationName(applicationName)
                .details(exception.getMessage())
                .timestamp(Instant.now());
        return new ResponseEntity<>(errorResponse.build(), HttpStatus.NOT_FOUND);
    }


    @ResponseBody
    @ExceptionHandler(BudgetNotEnoughException.class)
    ResponseEntity<ErrorResponse> budgetNotEnoughException(BudgetNotEnoughException exception){
        final ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder()
                .applicationName(applicationName)
                .details(exception.getMessage())
                .timestamp(Instant.now());
        return new ResponseEntity<>(errorResponse.build(), HttpStatus.PAYMENT_REQUIRED);
    }
}
