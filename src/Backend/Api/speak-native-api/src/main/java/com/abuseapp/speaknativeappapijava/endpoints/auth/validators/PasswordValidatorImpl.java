package com.abuseapp.speaknativeappapijava.endpoints.auth.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordValidatorImpl implements  PasswordValidator{

    private final Environment environment;

    @Autowired
    public PasswordValidatorImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public List<String> Validate(String password) {
        var validationResult = new ArrayList<String>();

        if(password == null || password.length() == 0)
            validationResult.add(
                    environment.getProperty("${validation.password.empty.message}"));

        int minPasswordLen = Integer.valueOf(environment.getProperty("user.password.minlen"));

        if(password.length() < minPasswordLen)
            validationResult.add(
                    String.format(
                            environment.getProperty("validation.password.minlen.message"),
                            minPasswordLen));

        return validationResult;
    }
}
