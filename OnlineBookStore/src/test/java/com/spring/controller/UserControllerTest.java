package com.spring.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.entity.BuyBook;
import com.spring.entity.Users;
import com.spring.security.JwtUtil;
import com.spring.service.BuyBookService;
import com.spring.service.UserServices;

class UserControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserServices userService;

    @Mock
    private BuyBookService buyBookService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserUpdate() {
        // Arrange
        Long uid = 1L;
        Users user = new Users();
        when(userService.updateUser(uid, user)).thenReturn(ResponseEntity.ok("update"));

        // Act
        ResponseEntity<String> response = userController.userupdate(uid, user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("update", response.getBody());
    }

    @Test
    void testBuyBook() {
        // Arrange
        BuyBook book = new BuyBook();
        String token = "Bearer token";
        String email = "test@example.com";
        when(jwtUtil.extractUsername("token")).thenReturn(email);
        when(buyBookService.addBuyBook(any(BuyBook.class))).thenReturn(ResponseEntity.ok("Added book"));

        // Act
        ResponseEntity<String> response = userController.buybook(book, token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Added book", response.getBody());
        assertEquals(email, book.getEmail());
    }

    @Test
    void testDeleteBuyBook() {
        // Arrange
        BuyBook book = new BuyBook();
        String token = "Bearer token";
        String email = "test@example.com";
        when(jwtUtil.extractUsername("token")).thenReturn(email);
        when(buyBookService.removeBuyBook(any(BuyBook.class))).thenReturn(ResponseEntity.ok("Deleted buy book"));

        // Act
        ResponseEntity<String> response = userController.deleteBuyBook(book, token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted buy book", response.getBody());
        assertEquals(email, book.getEmail());
    }

    @Test
    void testGetAllBuyBooks() {
        // Arrange
        String token = "Bearer token";
        String email = "test@example.com";
        when(jwtUtil.extractUsername("token")).thenReturn(email);
        List<BuyBook> buyBooks = new ArrayList<>();
        buyBooks.add(new BuyBook());
        when(buyBookService.getAllBuyBook(email)).thenReturn(ResponseEntity.ok(buyBooks));

        // Act
        ResponseEntity<List<BuyBook>> response = userController.getAllBuyBooks(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(buyBooks, response.getBody());
    }
}

