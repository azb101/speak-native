package com.abuseapp.speaknativeapi.infrastructure.security;

import javax.servlet.ServletException;

public class InvalidTokenException extends ServletException {
    public InvalidTokenException(String message){
        super(message);
    }
}
