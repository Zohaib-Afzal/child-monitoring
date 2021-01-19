package com.example.childmonitoring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public Validation() {
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
    public void validatePassword(){

    }
    public int validateIsLoginEmpty(String email, String password){
        if(email.isEmpty() && password.isEmpty()){
            return 0;
        } else if(email.isEmpty()) {
            return 1;
        } else if(password.isEmpty()) {
            //both are empty
            return 2;
        }
        //none are empty
        return 3;
    }
}
