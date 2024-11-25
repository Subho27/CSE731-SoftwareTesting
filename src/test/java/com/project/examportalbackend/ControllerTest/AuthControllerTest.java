package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.AuthController;
import com.project.examportalbackend.models.*;
import com.project.examportalbackend.services.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @Test
    public void testRegisterUser_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jDoe");

        when(authService.registerUserService(user)).thenReturn(user);

        User response = authController.registerUser(user);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("jDoe", response.getUsername());
        verify(authService, times(1)).registerUserService(user);
    }

    @Test
    public void testRegisterUser_Failure() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jDoe");

        when(authService.registerUserService(user)).thenThrow(new Exception("Registration failed"));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.registerUser(user);
        });

        assertEquals("Registration failed", exception.getMessage());
        verify(authService, times(1)).registerUserService(user);
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest("john.doe", "password123");

        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        LoginResponse loginResponse = new LoginResponse(user, "mock-jwt-token");

        when(authService.loginUserService(loginRequest)).thenReturn(loginResponse);

        LoginResponse response = authController.loginUser(loginRequest);

        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertEquals("John", response.getUser().getFirstName());
        assertEquals("Doe", response.getUser().getLastName());
        verify(authService, times(1)).loginUserService(loginRequest);
    }

    @Test
    public void testLoginUser_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest("john.doe", "wrongPassword");

        when(authService.loginUserService(loginRequest)).thenThrow(new Exception("Invalid credentials"));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.loginUser(loginRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authService, times(1)).loginUserService(loginRequest);
    }

}
