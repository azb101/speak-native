package com.abuseapp.speaknativeappapijava.infrastructure.exception_handling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void logAll(Exception ex){
        System.out.println("App: ");
        ex.printStackTrace();
    }
}
