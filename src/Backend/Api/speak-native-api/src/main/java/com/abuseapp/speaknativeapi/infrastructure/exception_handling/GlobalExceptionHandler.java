package com.abuseapp.speaknativeapi.infrastructure.exception_handling;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void logAll(Exception ex){
        System.out.println("App: ");
        ex.printStackTrace();
    }
}
