package com.userregistration;
public class InvalidUserDetailsException extends Exception {
    public InvalidUserDetailsException(String message) {
        super(message);
    }
}
