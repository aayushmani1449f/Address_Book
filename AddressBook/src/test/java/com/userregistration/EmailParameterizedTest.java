package com.userregistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailParameterizedTest {

    private String email;
    private boolean expectedResult;
    private UserRegistration registration;

    public EmailParameterizedTest(String email, boolean expectedResult) {
        this.email = email;
        this.expectedResult = expectedResult;
        this.registration = new UserRegistration();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // Valid cases
            {"abc@yahoo.com", true},
            {"abc-100@yahoo.com", true},
            {"abc.100@yahoo.com", true},
            {"abc111@abc.com", true},
            {"abc-100@abc.net", true},
            {"abc.100@abc.com.au", true},
            {"abc@1.com", true},
            {"abc@gmail.com.com", true},
            {"abc+100@gmail.com", true},
            // Invalid cases
            {"abc", false},
            {"abc@.com.my", false},
            {"abc123@gmail.a", false},
            {"abc123@.com", false},
            {"abc123@.com.com", false},
            {".abc@abc.com", false},
            {"abc()*@gmail.com", false},
            {"abc@%*.com", false},
            {"abc..2002@gmail.com", false},
            {"abc.@gmail.com", false},
            {"abc@abc@gmail.com", false},
            {"abc@gmail.com.1a", false},
            {"abc@gmail.com.aa.au", false}
        });
    }

    @Test
    public void testEmailValidation() {
        try {
            boolean result = registration.validateEmail.validate(this.email);
            assertEquals(expectedResult, result);
        } catch (InvalidUserDetailsException e) {
            assertEquals(expectedResult, false);
        }
    }
}
