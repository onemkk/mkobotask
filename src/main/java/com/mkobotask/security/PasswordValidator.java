package com.mkobotask.security;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public PasswordValidator() {

        pattern = Pattern.compile(PASSWORD_REGEX);
    }

    public boolean validate(final String password)
    {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
