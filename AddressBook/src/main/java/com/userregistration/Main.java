package com.userregistration;


public class Main {
    public static void main(String[] args) {
        UserRegistration registration = new UserRegistration();
        
        test(registration.validateFirstName, "Aayush");
        test(registration.validateFirstName, "aa");
        test(registration.validateLastName, "Mani");
        test(registration.validateLastName, "ma");
        test(registration.validateEmail, "abc.xyz@bl.co.in");
        test(registration.validateEmail, "abc@bl.co");
        test(registration.validateMobile, "91 9919819801");
        test(registration.validateMobile, "919919819801");
        test(registration.validatePasswordRule1, "password123");
        test(registration.validatePasswordRule1, "pass");
        test(registration.validatePasswordRule2, "Password123");
        test(registration.validatePasswordRule2, "password123");
        test(registration.validatePasswordRule3, "Password123");
        test(registration.validatePasswordRule3, "Password");
        test(registration.validatePasswordRule4, "Password@123");
        test(registration.validatePasswordRule4, "Password123");

        String[] validEmails = {
            "abc@yahoo.com", "abc-100@yahoo.com", "abc.100@yahoo.com", 
            "abc111@abc.com", "abc-100@abc.net", "abc.100@abc.com.au", 
            "abc@1.com", "abc@gmail.com.com", "abc+100@gmail.com"
        };
        System.out.println("\nValid Emails:");
        for (String sample : validEmails) {
            testWithInput(registration.validateEmail, sample);
        }

        String[] invalidEmails = {
            "abc", "abc@.com.my", "abc123@gmail.a", "abc123@.com", 
            "abc123@.com.com", ".abc@abc.com", "abc()*@gmail.com", 
            "abc@%*.com", "abc..2002@gmail.com", "abc.@gmail.com", 
            "abc@abc@gmail.com", "abc@gmail.com.1a", "abc@gmail.com.aa.au"
        };
        System.out.println("\nInvalid Emails:");
        for (String sample : invalidEmails) {
            testWithInput(registration.validateEmail, sample);
        }
    }

    private static void test(IUserValidation validator, String input) {
        try {
            System.out.println(validator.validate(input));
        } catch (InvalidUserDetailsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testWithInput(IUserValidation validator, String input) {
        try {
            System.out.println(input + " : " + validator.validate(input));
        } catch (InvalidUserDetailsException e) {
            System.out.println(input + " : " + e.getMessage());
        }
    }
}
