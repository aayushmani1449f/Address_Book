package com.userregistration;
import java.util.regex.Pattern;

public class UserRegistration {
    public IUserValidation validateFirstName = (firstName) -> {
        if (!Pattern.matches("^[A-Z][a-zA-Z]{2,}$", firstName)) {
            throw new InvalidUserDetailsException("Invalid First Name");
        }
        return true;
    };

    public IUserValidation validateLastName = (lastName) -> {
        if (!Pattern.matches("^[A-Z][a-zA-Z]{2,}$", lastName)) {
            throw new InvalidUserDetailsException("Invalid Last Name");
        }
        return true;
    };

    public IUserValidation validateEmail = (email) -> {
        if (!Pattern.matches("^[a-zA-Z0-9]+([._+-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$", email)) {
            throw new InvalidUserDetailsException("Invalid Email");
        }
        return true;
    };

    public IUserValidation validateMobile = (mobile) -> {
        if (!Pattern.matches("^[0-9]{2}\\s[0-9]{10}$", mobile)) {
            throw new InvalidUserDetailsException("Invalid Mobile Number");
        }
        return true;
    };

    public IUserValidation validatePasswordRule1 = (password) -> {
        if (!Pattern.matches("^.{8,}$", password)) {
            throw new InvalidUserDetailsException("Invalid Password");
        }
        return true;
    };

    public IUserValidation validatePasswordRule2 = (password) -> {
        if (!Pattern.matches("^(?=.*[A-Z]).{8,}$", password)) {
            throw new InvalidUserDetailsException("Invalid Password");
        }
        return true;
    };

    public IUserValidation validatePasswordRule3 = (password) -> {
        if (!Pattern.matches("^(?=.*[A-Z])(?=.*[0-9]).{8,}$", password)) {
            throw new InvalidUserDetailsException("Invalid Password");
        }
        return true;
    };

    public IUserValidation validatePasswordRule4 = (password) -> {
        if (!Pattern.matches("^(?=.*[A-Z])(?=.*[0-9])(?=[a-zA-Z0-9]*[^a-zA-Z0-9][a-zA-Z0-9]*$).{8,}$", password)) {
            throw new InvalidUserDetailsException("Invalid Password");
        }
        return true;
    };
}
