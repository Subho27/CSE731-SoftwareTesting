package com.project.examportalbackend.ServiceImplTest;

import com.project.examportalbackend.models.User;
import com.project.examportalbackend.repository.UserRepository;
import com.project.examportalbackend.services.implementation.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Set up test data
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Arrange: Mock the behavior of userRepository
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act: Call the method
        User result = (User) userDetailsService.loadUserByUsername("testuser");

        // Assert: Verify the results
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password123", result.getPassword());

        // Verify that the repository's findByUsername method was called exactly once
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange: Mock the behavior of userRepository to return null
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        // Act & Assert: Verify that a UsernameNotFoundException is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistentuser");
        });

        // Verify that the repository's findByUsername method was called exactly once
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }
}

