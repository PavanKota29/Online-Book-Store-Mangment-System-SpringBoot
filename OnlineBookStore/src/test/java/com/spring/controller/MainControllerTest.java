package com.spring.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.spring.entity.UsernamePass;
import com.spring.entity.Users;

import com.spring.security.CustomUserDetails;
import com.spring.security.JwtUtil;
import com.spring.service.UserServices;


import static org.mockito.Mockito.mock;
import org.springframework.security.core.userdetails.UserDetails;



class MainControllerTest {

    @Mock
    private DaoAuthenticationProvider authenticationProvider;

    @Mock
    private CustomUserDetails customUserDetails;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserServices userService;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdminLogin() {
        // Act
        ResponseEntity<String> response = mainController.adminLogin();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin Login", response.getBody());
    }

    @Test
    void testUserLogin() {
        // Act
        ResponseEntity<String> response = mainController.userLogin();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Login", response.getBody());
    }

    @Test
    void testUserReg() {
        // Arrange
        Users user = new Users();
        when(userService.registerUser(any(Users.class))).thenReturn(ResponseEntity.ok("inserted"));

        // Act
        ResponseEntity<String> response = mainController.userReg(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("inserted", response.getBody());
    }

    @Test
    void testGetTokenWithValidCredentials() throws Exception {
        // Arrange
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationToken);
        when(customUserDetails.loadUserByUsername(any(String.class))).thenReturn(userDetails);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("testToken");

        // Act
        ResponseEntity<String> response = mainController.getToken(new UsernamePass("email", "password"));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testToken", response.getBody());
    }

    @Test
    void testGetTokenWithInvalidCredentials() {
        // Arrange
        when(authenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        // Act and Assert
        assertThrows(BadCredentialsException.class, () -> {
            mainController.getToken(new UsernamePass("email", "password"));
        });
    }
}


