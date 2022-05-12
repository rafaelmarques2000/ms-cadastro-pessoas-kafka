package com.rafa.kafkaestudo.application.exceptions;

import com.rafa.kafkaestudo.application.response.ErrorResponse;
import com.rafa.kafkaestudo.domain.person.exception.PersonNotFoundException;
import com.rafa.kafkaestudo.infra.exception.InfraException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Component
public class GlobalHandlerException {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorResponse> handlerApiException(Throwable exception) {

        if(exception instanceof PersonNotFoundException) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
        }

        if(exception instanceof InfraException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
    }

}
