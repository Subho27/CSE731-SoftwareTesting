package com.project.examportalbackend.ConfigurationsTest;

import com.project.examportalbackend.configurations.JwtRequestFilter;
import com.project.examportalbackend.configurations.JwtUtil;
import com.project.examportalbackend.models.Role;
import com.project.examportalbackend.models.User;
import com.project.examportalbackend.services.implementation.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtRequestFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new Object())
                .addFilter(jwtRequestFilter)
                .build();
    }

    @Test
    public void testFilter_ValidToken() throws Exception {
        String jwtToken = "valid.jwt.token";
        String username = "testUser";

        // Mock JWT utility behavior
        when(jwtUtil.extractUsername(jwtToken)).thenReturn(username);
        when(jwtUtil.validateToken(eq(jwtToken), any())).thenReturn(true);

        // Mock Role and authorities
        Role role = new Role();
        role.setRoleName("USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Mock UserDetails behavior
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setRoles(roles);

        // Mock the userDetailsServiceImpl to return the mocked user
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(user);

        // Perform the request
        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());

        // Verify interactions
        verify(jwtUtil, times(1)).extractUsername(jwtToken);
        verify(jwtUtil, times(1)).validateToken(jwtToken, user);
        verify(userDetailsServiceImpl, times(1)).loadUserByUsername(username);
    }



    @Test
    public void testFilter_MissingAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/test-endpoint"))
                .andExpect(status().isForbidden());

        // No interactions with mocks should occur
        verifyNoInteractions(jwtUtil);
        verifyNoInteractions(userDetailsServiceImpl);
    }

    @Test
    public void testFilter_InvalidToken_NoBearer() throws Exception {
        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "InvalidToken"))
                .andExpect(status().isForbidden());

        // No interactions with mocks should occur
        verifyNoInteractions(jwtUtil);
        verifyNoInteractions(userDetailsServiceImpl);
    }

    @Test
    public void testFilter_TokenWithIllegalArgumentException() throws Exception {
        String jwtToken = "invalid.jwt.token";

        // Mock exception for extractUsername
        when(jwtUtil.extractUsername(jwtToken)).thenThrow(new IllegalArgumentException("Unable to get JWT Token"));

        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());

        // Verify interactions
        verify(jwtUtil, times(1)).extractUsername(jwtToken);
    }

    @Test
    public void testFilter_TokenExpired() throws Exception {
        String jwtToken = "expired.jwt.token";

        // Mock exception for extractUsername
        when(jwtUtil.extractUsername(jwtToken)).thenThrow(new ExpiredJwtException(null, null, "JWT Token has expired"));

        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());

        // Verify interactions
        verify(jwtUtil, times(1)).extractUsername(jwtToken);
    }

    @Test
    public void testFilter_InvalidToken_ValidationFails() throws Exception {
        String jwtToken = "valid.jwt.token";
        String username = "testUser";

        // Mock JWT utility behavior
        when(jwtUtil.extractUsername(jwtToken)).thenReturn(username);
        when(jwtUtil.validateToken(eq(jwtToken), any())).thenReturn(false);  // Token validation fails

        // Perform the request with the invalid token
        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());  // Expecting 403 Forbidden

        // Verify interactions (no interaction with userDetailsServiceImpl expected)
        verify(jwtUtil, times(1)).extractUsername(jwtToken);  // verify extractUsername was called once
        verify(jwtUtil, times(1)).validateToken(eq(jwtToken), any());  // verify validateToken was called once
    }

    @Test
    public void testFilter_InvalidToken_ValidationFails1() throws Exception {
        String jwtToken = "invalid.jwt.token";

        when(jwtUtil.validateToken(eq(jwtToken), any())).thenReturn(false);

        mockMvc.perform(get("/test-endpoint")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());

        // Verify that userDetailsServiceImpl is not called
        verify(userDetailsServiceImpl, never()).loadUserByUsername(anyString());
    }

}

