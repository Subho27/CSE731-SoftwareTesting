package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.LoginRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginRequestTest {

    @Test
    public void testSetters() {
        LoginRequest loginRequest = new LoginRequest("initialUsername", "initialPassword");

        // Verify initial values
        assertEquals("initialUsername", loginRequest.getUsername());
        assertEquals("initialPassword", loginRequest.getPassword());

        // Set new values using setters
        loginRequest.setUsername("newUsername");
        loginRequest.setPassword("newPassword");

        // Verify updated values
        assertEquals("newUsername", loginRequest.getUsername());
        assertEquals("newPassword", loginRequest.getPassword());
    }

}
