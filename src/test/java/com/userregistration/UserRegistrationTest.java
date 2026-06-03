package com.userregistration;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class UserRegistrationTest {

    private final UserRegistration registration = new UserRegistration();

    @Test
    public void testFirstNameHappy() throws InvalidUserDetailsException {
        assertTrue(registration.validateFirstName.validate("Aayush"));
    }

    @Test
    public void testFirstNameSad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validateFirstName.validate("aa"));
    }

    @Test
    public void testLastNameHappy() throws InvalidUserDetailsException {
        assertTrue(registration.validateLastName.validate("Mani"));
    }

    @Test
    public void testLastNameSad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validateLastName.validate("ma"));
    }

    @Test
    public void testEmailHappy() throws InvalidUserDetailsException {
        assertTrue(registration.validateEmail.validate("abc.xyz@bl.co.in"));
    }

    @Test
    public void testEmailSad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validateEmail.validate("abc@.com.my"));
    }

    @Test
    public void testMobileHappy() throws InvalidUserDetailsException {
        assertTrue(registration.validateMobile.validate("91 9919819801"));
    }

    @Test
    public void testMobileSad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validateMobile.validate("919919819801"));
    }

    @Test
    public void testPasswordRule1Happy() throws InvalidUserDetailsException {
        assertTrue(registration.validatePasswordRule1.validate("password123"));
    }

    @Test
    public void testPasswordRule1Sad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validatePasswordRule1.validate("pass"));
    }

    @Test
    public void testPasswordRule2Happy() throws InvalidUserDetailsException {
        assertTrue(registration.validatePasswordRule2.validate("Password123"));
    }

    @Test
    public void testPasswordRule2Sad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validatePasswordRule2.validate("password123"));
    }

    @Test
    public void testPasswordRule3Happy() throws InvalidUserDetailsException {
        assertTrue(registration.validatePasswordRule3.validate("Password123"));
    }

    @Test
    public void testPasswordRule3Sad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validatePasswordRule3.validate("Password"));
    }

    @Test
    public void testPasswordRule4Happy() throws InvalidUserDetailsException {
        assertTrue(registration.validatePasswordRule4.validate("Password@123"));
    }

    @Test
    public void testPasswordRule4Sad() {
        assertThrows(InvalidUserDetailsException.class, () -> registration.validatePasswordRule4.validate("Password123"));
    }
}
