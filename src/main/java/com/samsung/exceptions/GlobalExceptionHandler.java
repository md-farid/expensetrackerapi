package com.samsung.exceptions;

import com.samsung.models.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ErrorModel> handleExpenseNotFoundException(ExpenseNotFoundException ex, WebRequest req){
        ErrorModel model = new ErrorModel();
        model.setStatusCode(HttpStatus.NOT_FOUND.value());
        model.setMessage(ex.getMessage());
        model.setTimestamp(new Date());
        return new ResponseEntity<ErrorModel>(model,HttpStatus.NOT_FOUND);
    }
}
