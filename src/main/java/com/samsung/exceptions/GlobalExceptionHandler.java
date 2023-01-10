package com.samsung.exceptions;

import com.samsung.models.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorModel> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest req){
        ErrorModel model = new ErrorModel();
        model.setStatusCode(HttpStatus.NOT_FOUND.value());
        model.setMessage(ex.getMessage());
        model.setTimestamp(new Date());
        return new ResponseEntity<ErrorModel>(model,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest req){
        ErrorModel model = new ErrorModel();
        model.setStatusCode(HttpStatus.BAD_REQUEST.value());
        model.setMessage(ex.getMessage());
        model.setTimestamp(new Date());
        return new ResponseEntity<ErrorModel>(model,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleGeneralException(Exception ex, WebRequest req){
        ErrorModel model = new ErrorModel();
        model.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.setMessage(ex.getMessage());
        model.setTimestamp(new Date());
        return new ResponseEntity<ErrorModel>(model,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleItemExistsException(ItemAlreadyExistsException ex, WebRequest req){
        ErrorModel model = new ErrorModel();
        model.setStatusCode(HttpStatus.CONFLICT.value());
        model.setMessage(ex.getMessage());
        model.setTimestamp(new Date());
        return new ResponseEntity<ErrorModel>(model,HttpStatus.CONFLICT);
    }
}
