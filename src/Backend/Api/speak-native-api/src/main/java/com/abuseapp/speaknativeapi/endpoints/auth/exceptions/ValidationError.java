package com.abuseapp.speaknativeapi.endpoints.auth.exceptions;

import java.util.List;

public class ValidationError extends Exception {

    public ValidationError(String message) {
        super(message);
    }

    public static ValidationError fromMessages(List<String> messages)
    {
        var sb = new StringBuilder();
        sb.append("Validation failed with following errors: \n");

        for(String message : messages)
            sb.append(message);

        return new ValidationError(sb.toString());
    }
}
