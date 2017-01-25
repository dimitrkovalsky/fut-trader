package com.liberty.controllers;

import com.liberty.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class ApplicationControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleGeneralException(HttpServletRequest request, Exception e) {
        log.error("Internal server error", e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR,
                "Internal server error on " + request.getRequestURL(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ErrorResponse handleApplicationException(HttpServletRequest req, ApplicationException e) {
        log.error("Application error on " + req.getRequestURL(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse handleNotFoundException(HttpServletRequest request, NotFoundException e) {
        log.error("Resource not found error on " + request.getRequestURL());

        return new ErrorResponse(HttpStatus.NOT_FOUND, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException e) {
        log.error("Illegal argument error on " + req.getRequestURL(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ErrorCode.ILLEGAL_ARGUMENT_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ErrorResponse handleValidationException(HttpServletRequest req, ValidationException e) {

        log.error("Validation error on " + req.getRequestURL(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ErrorCode.ILLEGAL_ARGUMENT_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(OperationFailedException.class)
    @ResponseBody
    public ErrorResponse handleOperationFailedException(HttpServletRequest req, OperationFailedException e) {
        log.error("operation failed on " + req.getRequestURL(), e);
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, e);
    }
}
