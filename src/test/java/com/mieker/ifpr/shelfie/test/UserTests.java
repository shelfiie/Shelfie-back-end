package com.mieker.ifpr.shelfie.test;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTests {

//    @Mock
//    private AuthenticationService authService;

    @Test
    public void testEmailValidation() {
        String emailAddress = "usernamesomething@domain.com";
        assertTrue(Validation.emailValidation(emailAddress));
    }
}
