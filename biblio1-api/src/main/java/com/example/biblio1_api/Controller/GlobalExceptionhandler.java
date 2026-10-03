package com.example.biblio1_api.Controller;

import com.example.biblio1_api.Dto.ErrorResponse;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionhandler {
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<ErrorResponse> handlerNotfound(RessourceIntrouvableException ex)
    {
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConflitMetierException.class)
    public ResponseEntity<ErrorResponse> handlerConflitMetier(ConflitMetierException ex)
    {
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid data");
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),message);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception ex)
    {
        HttpStatus status;

        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalStateException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(), status.value(),ex.getMessage());
        return new ResponseEntity<>(error,status);
    }
}
