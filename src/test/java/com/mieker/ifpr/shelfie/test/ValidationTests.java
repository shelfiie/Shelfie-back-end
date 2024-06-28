package com.mieker.ifpr.shelfie.test;

import com.mieker.ifpr.shelfie.config.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidationTests {

//    @Mock
//    private AuthenticationService authService;

    @Test
    public void testEmailValidation() {
        String emailAddress = "usernamesomething@domain.com";
        assertTrue(Validation.emailValidation(emailAddress));
    }
}
