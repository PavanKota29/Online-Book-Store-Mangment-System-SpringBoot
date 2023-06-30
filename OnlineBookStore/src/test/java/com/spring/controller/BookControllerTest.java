package com.spring.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.dtoentity.BookDTO;
import com.spring.service.BookService;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        BookDTO book1 = new BookDTO(1L, "Book 1", null, null);
        BookDTO book2 = new BookDTO(2L, "Book 2", null, null);
        List<BookDTO> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(ResponseEntity.ok(books));

        // Act
        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBooks() {
        // Arrange
        Long bookId = 1L;
        BookDTO book = new BookDTO(bookId, "Book 1", null, null);

        when(bookService.getBooks(bookId)).thenReturn(ResponseEntity.ok(book));

        // Act
        ResponseEntity<BookDTO> response = bookController.getBooks(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).getBooks(bookId);
    }

    @Test
    void testAddBooks() {
        // Arrange
        BookDTO book = new BookDTO(1L, "Book 1", null, null);

        when(bookService.addBooks(any(BookDTO.class))).thenReturn(ResponseEntity.ok("Book added successfully."));

        // Act
        ResponseEntity<String> response = bookController.addBooks(book);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book added successfully.", response.getBody());
        verify(bookService, times(1)).addBooks(book);
    }

    @Test
    void testUpdateBooks() {
        // Arrange
        Long bookId = 1L;
        BookDTO book = new BookDTO(bookId, "Book 1", null, null);

        when(bookService.updateBooks(bookId, book)).thenReturn(ResponseEntity.ok("Book updated successfully."));

        // Act
        ResponseEntity<String> response = bookController.updateBooks(bookId, book);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book updated successfully.", response.getBody());
        verify(bookService, times(1)).updateBooks(bookId, book);
    }

    @Test
    void testDeleteBooks() {
        // Arrange
        Long bookId = 1L;

        when(bookService.deleteBooks(bookId)).thenReturn(ResponseEntity.ok("Book deleted successfully."));

        // Act
        ResponseEntity<String> response = bookController.deleteBooks(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book deleted successfully.", response.getBody());
        verify(bookService, times(1)).deleteBooks(bookId);
    }
}

