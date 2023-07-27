package com.flz.mydubbo.businessservice.exception.handler;

import com.flz.mydubbo.common.dto.ErrorResponse;
import org.apache.dubbo.remoting.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler
    public ErrorResponse handleDubboTimeoutException(TimeoutException e) {
        return ErrorResponse.of(HttpStatus.REQUEST_TIMEOUT.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse handleException(Exception e) {
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
