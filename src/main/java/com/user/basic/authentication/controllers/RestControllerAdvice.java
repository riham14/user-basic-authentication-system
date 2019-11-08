package com.user.basic.authentication.controllers;

import com.user.basic.authentication.exceptions.ErrorException;
import com.user.basic.authentication.exceptions.ResponseException;
import org.apache.tomcat.jni.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(ErrorException.class)
    protected ResponseEntity<ResponseException> handleException(ErrorException ex, WebRequest request){
        ResponseException responseException = new ResponseException();
        responseException.setErrors(ex.getErrors());
        return new ResponseEntity<>(responseException, ex.getStatus());
    }
}
