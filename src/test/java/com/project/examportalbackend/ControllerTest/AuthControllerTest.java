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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

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
        // Prepare a User object with all required fields
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jDoe");
        user.setPassword("password123");
        user.setPhoneNumber("123-456-7890");
        user.setActive(true);

        // Mocking the service method to return the user
        when(authService.registerUserService(user)).thenReturn(user);

        // Call the controller method
        User response = authController.registerUser(user);

        // Assertions for basic fields
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("jDoe", response.getUsername());
        assertEquals("password123", response.getPassword());  // Checking password
        assertEquals("123-456-7890", response.getPhoneNumber());  // Checking phone number
        assertTrue(response.isActive());  // Checking if active status is correct

        // Verify the service method was called once
        verify(authService, times(1)).registerUserService(user);

        // Verifying if UserDetails methods are working properly
        assertTrue(response.isAccountNonExpired());
        assertTrue(response.isAccountNonLocked());
        assertTrue(response.isCredentialsNonExpired());
        assertTrue(response.isEnabled());
    }

    @Test
    public void testUserDetailsMethods_Success() {
        // Prepare a User object
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jDoe");
        user.setPassword("password123");
        user.setPhoneNumber("123-456-7890");
        user.setActive(true);

        // Mock roles for user
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        user.setRoles(Set.of(role));  // Assign a role to the user

        // Verify the UserDetails methods
        assertTrue(user.isAccountNonExpired()); // Ensure account is not expired
        assertTrue(user.isAccountNonLocked());  // Ensure account is not locked
        assertTrue(user.isCredentialsNonExpired()); // Ensure credentials are not expired
        assertTrue(user.isEnabled());  // Ensure account is enabled

        // Check authorities (roles)
        assertEquals(1, user.getAuthorities().size()); // One role assigned
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testUserToString_Success() {
        // Prepare a User object
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("jDoe");

        // Verify the toString method includes expected values
        String userString = user.toString();
        assertTrue(userString.contains("John"));  // Check if first name is included
        assertTrue(userString.contains("Doe"));  // Check if last name is included
        assertTrue(userString.contains("jDoe")); // Check if username is included
    }

    @Test
    public void testAllArgsConstructor() {
        // Create a User object using the @AllArgsConstructor-generated constructor
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        Set<Role> roles = Set.of(role);

        User user = new User(
                1L,                    // userId
                "John",                // firstName
                "Doe",                 // lastName
                "jDoe",                // username
                "password123",         // password
                "123-456-7890",        // phoneNumber
                true,                  // isActive
                roles                  // roles
        );

        // Verify all fields are correctly initialized
        assertEquals(1L, user.getUserId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("jDoe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("123-456-7890", user.getPhoneNumber());
        assertTrue(user.isActive());
        assertEquals(1, user.getRoles().size());  // Verify one role is set
        assertTrue(user.getRoles().contains(role)); // Check if the role is correctly assigned

        // Verify UserDetails methods (optional, to ensure everything is consistent)
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
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
