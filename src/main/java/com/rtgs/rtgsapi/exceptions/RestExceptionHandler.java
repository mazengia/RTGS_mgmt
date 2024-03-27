/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rtgs.rtgsapi.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NonUniqueResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

/**
 * @author birhane
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistsException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex.getMessage(),ex));
    }
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(AlreadyAuthorizedException.class)
    protected ResponseEntity<Object> handleAlreadyAuthorizedException(AlreadyAuthorizedException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex.getMessage(),ex));
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExistException(EntityExistsException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex.getMessage(),ex));
    }
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NonUniqueResultException.class)
    protected ResponseEntity<Object> handleEntityExistException(NonUniqueResultException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex.getMessage(),ex));
    }
    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex.getMessage(),ex));
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(UnAuthorizedActionException.class)
    protected ResponseEntity<Object> handleUnAuthorizedActionException(UnAuthorizedActionException ex) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(),ex));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }



    /**
     * Handle jakarta.persistence.EntityNotFoundException
     */
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(jakarta.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(new ApiError(NOT_FOUND, ex));
    }



    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
