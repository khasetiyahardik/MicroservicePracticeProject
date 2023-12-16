package com.example.authentication.exception.handler;

import com.example.authentication.dto.ResponseDTO;
import com.example.authentication.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO handleAdminNotFound(UserNotFoundException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }
}
