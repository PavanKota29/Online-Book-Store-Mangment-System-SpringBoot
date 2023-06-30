package com.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.entity.Users;
import com.spring.repo.UserRepo;

class UserServicesTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServices userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserWithValidUser() {
        // Arrange
        Users user = new Users();
        user.setUname("John");
        user.setUphone("1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepo.save(any(Users.class))).thenReturn(new Users());

        // Act
        ResponseEntity<String> response = userServices.registerUser(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("inserted", response.getBody());
        verify(userRepo, times(1)).save(any(Users.class));
    }

    @Test
    void testRegisterUserWithException() {
        // Arrange
        Users user = new Users();
        user.setUname("John");
        user.setUphone("1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepo.save(any(Users.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<String> response = userServices.registerUser(user);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userRepo, times(1)).save(any(Users.class));
    }

    @Test
    void testUpdateUserWithExistingUser() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        user.setUname("John");
        user.setUphone("1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");
        Users existingUser = new Users();
        existingUser.setUid(userId);

        when(userRepo.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepo.save(any(Users.class))).thenReturn(existingUser);

        // Act
        ResponseEntity<String> response = userServices.updateUser(userId, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("update", response.getBody());
        verify(userRepo, times(1)).findById(userId);
        verify(userRepo, times(1)).save(any(Users.class));
    }

    @Test
    void testUpdateUserWithNonExistingUser() {
        // Arrange
        Long userId = 1L;
        Users user = new Users();
        user.setUname("John");
        user.setUphone("1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepo.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act
        ResponseEntity<String> response = userServices.updateUser(userId, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userRepo, times(1)).findById(userId);
    }

    @Test
    void testPasswordEncoder() {
        // Arrange
        UserServices userService = new UserServices();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Act
        PasswordEncoder actualEncoder = userService.passwordEncoder();

        // Assert
        assertEquals(passwordEncoder.getClass(), actualEncoder.getClass());
    }
}

