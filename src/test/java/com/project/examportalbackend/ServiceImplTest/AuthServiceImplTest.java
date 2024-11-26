package com.project.examportalbackend.ServiceImplTest;


import com.project.examportalbackend.configurations.JwtUtil;
import com.project.examportalbackend.models.*;
import com.project.examportalbackend.repository.*;
import com.project.examportalbackend.services.implementation.AuthServiceImpl;
import com.project.examportalbackend.services.implementation.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private LoginRequest loginRequest;
    private Role role;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        role = new Role("USER", "Standard User Role");
        loginRequest = new LoginRequest("username", "password");
    }

    // Test for registerUserService - user already exists
    @Test(expected = Exception.class)
    public void testRegisterUserService_UserAlreadyExists() throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user); // Simulate user already exists

        authService.registerUserService(user); // This should throw Exception("User Already Exists")
    }

    // Test for registerUserService - new user registration
    @Test
    public void testRegisterUserService_Success() throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null); // Simulate user doesn't exist
        when(roleRepository.findById("USER")).thenReturn(Optional.of(role)); // Simulate role found
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = authService.registerUserService(user);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    // Test for loginUserService - valid credentials
    @Test
    public void testLoginUserService_Success() throws Exception {
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user); // Simulate user exists
        when(userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername())).thenReturn(user);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("token");

        LoginResponse result = authService.loginUserService(loginRequest);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals("token", result.getJwtToken());
    }

    // Test for loginUserService - invalid credentials (BadCredentialsException)
    @Test
    public void testLoginUserService_InvalidCredentials() throws Exception {
        lenient().when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user); // Simulate user exists
        lenient().when(userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername())).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(Exception.class, () -> authService.loginUserService(loginRequest)); // This should throw Exception
    }

    @Test
    public void testLoginUserService_UserDisabled_Exception() throws Exception {
        // Arrange: Mock the authenticationManager to throw DisabledException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("USER_DISABLED"));

        // Act & Assert: Verify that the exception is thrown
        Exception exception = assertThrows(Exception.class, () -> {
            authService.loginUserService(loginRequest);
        });

        assertEquals("USER_DISABLED", exception.getMessage());
    }

    @Test
    public void testLoginUserService_InvalidCredentials_Exception() throws Exception {
        // Arrange: Mock the authenticationManager to throw BadCredentialsException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));

        // Act & Assert: Verify that the exception is thrown
        Exception exception = assertThrows(Exception.class, () -> {
            authService.loginUserService(loginRequest);
        });

        assertEquals("INVALID_CREDENTIALS", exception.getMessage());
    }

}


