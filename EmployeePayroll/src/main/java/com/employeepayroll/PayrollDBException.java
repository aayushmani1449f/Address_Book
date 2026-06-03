package com.employeepayroll;
public class PayrollDBException extends RuntimeException {
    public PayrollDBException(String message) {
        super(message);
    }
    public PayrollDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
