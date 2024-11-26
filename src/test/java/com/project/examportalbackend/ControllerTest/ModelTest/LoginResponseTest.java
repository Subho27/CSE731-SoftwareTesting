package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.LoginResponse;
import com.project.examportalbackend.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginResponseTest {

    @Test
    public void testSetters() {
        // Create a User object to set in the LoginResponse
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Create a LoginResponse object
        LoginResponse loginResponse = new LoginResponse(user, "initialJwtToken");

        // Verify initial values
        assertEquals(user, loginResponse.getUser());
        assertEquals("initialJwtToken", loginResponse.getJwtToken());

        // Set new values using setters
        User newUser = new User();
        newUser.setUsername("newTestUser");
        newUser.setPassword("newTestPassword");

        loginResponse.setUser(newUser);
        loginResponse.setJwtToken("newJwtToken");

        // Verify updated values
        assertEquals(newUser, loginResponse.getUser());
        assertEquals("newJwtToken", loginResponse.getJwtToken());
    }
}

