package com.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.entity.BuyBook;
import com.spring.repo.BuyBookRepo;

import java.util.ArrayList;
import java.util.List;

import com.spring.exception.BookNotFound;
import com.spring.repo.BookRepo;

class BuyBookServiceTest {

    @Mock
    private BuyBookRepo buyBookRepo;

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BuyBookService buyBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBuyBook_WhenBuyBookDoesNotExist() {
        // Arrange
        BuyBook book = new BuyBook();
        book.setBname("Book1");
        book.setEmail("test@example.com");
        when(bookRepo.findByBname("Book1")).thenReturn(null);
        when(buyBookRepo.countBuyBook("Book1", "test@example.com")).thenReturn(0);

        // Act
        ResponseEntity<String> response = buyBookService.addBuyBook(book);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Added BuyBook", response.getBody());
    }



    @Test
    void testRemoveBuyBook_WhenQuentityIsZero() {
        // Arrange
        BuyBook book = new BuyBook();
        book.setBname("Book1");
        book.setEmail("test@example.com");
        book.setQuentity(0);
        when(buyBookRepo.findBuyBook("Book1", "test@example.com")).thenReturn(book);

        // Act
        ResponseEntity<String> response = buyBookService.removeBuyBook(book);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted BuyBook", response.getBody());
    }


    @Test
    void testGetAllBuyBook() {
        // Arrange
        String email = "test@example.com";
        List<BuyBook> buyBooks = new ArrayList<>();
        when(buyBookRepo.findAllBuyBook(email)).thenReturn(buyBooks);

        // Act
        ResponseEntity<List<BuyBook>> response = buyBookService.getAllBuyBook(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(buyBooks, response.getBody());
    }

    @Test
    void testGetAllBuyBook_WhenExceptionOccurs() {
        // Arrange
        String email = "test@example.com";
        when(buyBookRepo.findAllBuyBook(email)).thenThrow(new BookNotFound());

        // Act and Assert
        assertThrows(BookNotFound.class, () -> buyBookService.getAllBuyBook(email));
    }
}
