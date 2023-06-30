package com.spring.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.dtoentity.AuthorDTO;
import com.spring.service.AuthorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthor() {
        // Arrange
        AuthorDTO author1 = new AuthorDTO(1L, "John Doe", null, null);
        AuthorDTO author2 = new AuthorDTO(2L, "Jane Smith", null, null);
        List<AuthorDTO> authors = Arrays.asList(author1, author2);

        when(authorService.getAllAuthor()).thenReturn(ResponseEntity.ok(authors));

        // Act
        ResponseEntity<List<AuthorDTO>> response = authorController.getAllAuthor();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(authors, response.getBody());
        verify(authorService, times(1)).getAllAuthor();
    }

    @Test
    void testGetAuthor() {
        // Arrange
        Long authorId = 1L;
        AuthorDTO author = new AuthorDTO(authorId, "John Doe", null, null);

        when(authorService.getAuthor(authorId)).thenReturn(ResponseEntity.ok(author));

        // Act
        ResponseEntity<AuthorDTO> response = authorController.getAuthor(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).getAuthor(authorId);
    }

    @Test
    void testAddAuthor() {
        // Arrange
        AuthorDTO author = new AuthorDTO(1L, "John Doe", null, null);

        when(authorService.addAuthor(any(AuthorDTO.class))).thenReturn(ResponseEntity.ok("Author added successfully."));

        // Act
        ResponseEntity<String> response = authorController.addAuthor(author);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Author added successfully.", response.getBody());
        verify(authorService, times(1)).addAuthor(author);
    }

    @Test
    void testUpdateAuthor() {
        // Arrange
        Long authorId = 1L;
        AuthorDTO author = new AuthorDTO(authorId, "John Doe", null, null);

        when(authorService.updateAuthor(authorId, author)).thenReturn(ResponseEntity.ok("Author updated successfully."));

        // Act
        ResponseEntity<String> response = authorController.updateAuthor(authorId, author);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Author updated successfully.", response.getBody());
        verify(authorService, times(1)).updateAuthor(authorId, author);
    }

    @Test
    void testDeleteAuthor() {
        // Arrange
        Long authorId = 1L;

        when(authorService.deleteAuthor(authorId)).thenReturn(ResponseEntity.ok("Author deleted successfully."));

        // Act
        ResponseEntity<String> response = authorController.deleteAuthor(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Author deleted successfully.", response.getBody());
        verify(authorService, times(1)).deleteAuthor(authorId);
    }
}
