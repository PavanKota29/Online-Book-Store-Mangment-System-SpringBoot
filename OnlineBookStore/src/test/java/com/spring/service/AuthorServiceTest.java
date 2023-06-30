package com.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.dtoentity.AuthorDTO;
import com.spring.entity.Author;
import com.spring.entity.Book;
import com.spring.exception.AuthorNotFound;
import com.spring.exception.InsertingAuthorException;
import com.spring.repo.AuthorRepo;
import com.spring.repo.BookRepo;

class AuthorServiceTest {

    @Mock
    private AuthorRepo authorRepo;

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthorWithExistingAuthors() {
        // Arrange
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "John Doe", "john.doe@example.com", null));

        when(authorRepo.findAll()).thenReturn(authors);

        // Act
        ResponseEntity<List<AuthorDTO>> response = authorService.getAllAuthor();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getAname());
        assertEquals("john.doe@example.com", response.getBody().get(0).getAemail());
    }

    @Test
    void testGetAllAuthorWithNoAuthors() {
        // Arrange
        when(authorRepo.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(AuthorNotFound.class, () -> authorService.getAllAuthor());
    }

    @Test
    void testGetAuthorWithExistingAuthor() {
        // Arrange
        Long authorId = 1L;
        Author author = new Author(authorId, "John Doe", "john.doe@example.com", null);

        when(authorRepo.findById(authorId)).thenReturn(Optional.of(author));

        // Act
        ResponseEntity<AuthorDTO> response = authorService.getAuthor(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getAname());
        assertEquals("john.doe@example.com", response.getBody().getAemail());
    }

    @Test
    void testGetAuthorWithNonExistingAuthor() {
        // Arrange
        Long authorId = 1L;

        when(authorRepo.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFound.class, () -> authorService.getAuthor(authorId));
    }

    @Test
    void testAddAuthorWithValidAuthorDTO() {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAname("John Doe");
        authorDTO.setAemail("john.doe@example.com");

        when(authorRepo.save(any(Author.class))).thenReturn(new Author());

        // Act
        ResponseEntity<String> response = authorService.addAuthor(authorDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("insert", response.getBody());
        verify(authorRepo, times(1)).save(any(Author.class));
    }

    @Test
    void testAddAuthorWithException() {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAname("John Doe");
        authorDTO.setAemail("john.doe@example.com");

        when(authorRepo.save(any(Author.class))).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(InsertingAuthorException.class, () -> authorService.addAuthor(authorDTO));
        verify(authorRepo, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthorWithExistingAuthor() {
        // Arrange
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAname("John Doe");
        authorDTO.setAemail("john.doe@example.com");
        Author existingAuthor = new Author(authorId, "Old Name", "old.email@example.com", null);

        when(authorRepo.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepo.save(any(Author.class))).thenReturn(existingAuthor);

        // Act
        ResponseEntity<String> response = authorService.updateAuthor(authorId, authorDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("update", response.getBody());
        assertEquals("John Doe", existingAuthor.getAname());
        assertEquals("john.doe@example.com", existingAuthor.getAemail());
        verify(authorRepo, times(1)).findById(authorId);
        verify(authorRepo, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthorWithNonExistingAuthor() {
        // Arrange
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAname("John Doe");
        authorDTO.setAemail("john.doe@example.com");

        when(authorRepo.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFound.class, () -> authorService.updateAuthor(authorId, authorDTO));
        verify(authorRepo, times(1)).findById(authorId);
    }

    @Test
    void testDeleteAuthorWithExistingAuthor() {
        // Arrange
        Long authorId = 1L;
        Author existingAuthor = new Author(authorId, "John Doe", "john.doe@example.com", null);
        Book book1 = new Book(1L, "Book 1", existingAuthor, null);
        Book book2 = new Book(2L, "Book 2", existingAuthor, null);
        existingAuthor.setBook(List.of(book1, book2));

        when(authorRepo.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        // Act
        ResponseEntity<String> response = authorService.deleteAuthor(authorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("delete", response.getBody());
        verify(authorRepo, times(1)).findById(authorId);
        verify(bookRepo, times(1)).deleteAll(existingAuthor.getBook());
        verify(authorRepo, times(1)).deleteById(authorId);
    }

    @Test
    void testDeleteAuthorWithNonExistingAuthor() {
        // Arrange
        Long authorId = 1L;

        when(authorRepo.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFound.class, () -> authorService.deleteAuthor(authorId));
        verify(authorRepo, times(1)).findById(authorId);
    }
}

