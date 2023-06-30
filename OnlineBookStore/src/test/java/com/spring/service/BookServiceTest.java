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

import com.spring.dtoentity.BookDTO;
import com.spring.entity.Author;
import com.spring.entity.Book;
import com.spring.exception.BookNotFound;
import com.spring.exception.InsertingBookException;
import com.spring.repo.AuthorRepo;
import com.spring.repo.BookRepo;

class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooksWithExistingBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", new Author(), null));

        when(bookRepo.findAll()).thenReturn(books);

        // Act
        ResponseEntity<List<BookDTO>> response = bookService.getAllBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Book 1", response.getBody().get(0).getBname());
    }

    @Test
    void testGetAllBooksWithNoBooks() {
        // Arrange
        when(bookRepo.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(BookNotFound.class, () -> bookService.getAllBooks());
    }

    @Test
    void testGetBooksWithExistingBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", new Author(), null);

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<BookDTO> response = bookService.getBooks(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book 1", response.getBody().getBname());
    }

    @Test
    void testGetBooksWithNonExistingBook() {
        // Arrange
        Long bookId = 1L;

        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFound.class, () -> bookService.getBooks(bookId));
    }

    @Test
    void testAddBooksWithValidBookDTO() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBname("Book 1");
        Author author = new Author();
        author.setAid(1L);
        bookDTO.setAuthor(author);

        when(authorRepo.findById(1L)).thenReturn(Optional.of(new Author()));
        when(bookRepo.save(any(Book.class))).thenReturn(new Book());

        // Act
        ResponseEntity<String> response = bookService.addBooks(bookDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("insert", response.getBody());
        verify(authorRepo, times(1)).findById(1L);
        verify(bookRepo, times(1)).save(any(Book.class));
    }

    @Test
    void testAddBooksWithNonExistingAuthor() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBname("Book 1");
        Author author = new Author();
        author.setAid(1L);
        bookDTO.setAuthor(author);

        when(authorRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InsertingBookException.class, () -> bookService.addBooks(bookDTO));
        verify(authorRepo, times(1)).findById(1L);
    }

    @Test
    void testUpdateBooksWithExistingBook() {
        // Arrange
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBname("Updated Book");
        Author author = new Author();
        author.setAid(1L);
        bookDTO.setAuthor(author);
        Book existingBook = new Book(bookId, "Book 1", new Author(), null);

        when(authorRepo.findById(1L)).thenReturn(Optional.of(new Author()));
        when(bookRepo.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepo.save(any(Book.class))).thenReturn(existingBook);

        // Act
        ResponseEntity<String> response = bookService.updateBooks(bookId, bookDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("update", response.getBody());
        assertEquals("Updated Book", existingBook.getBname());
        verify(authorRepo, times(1)).findById(1L);
        verify(bookRepo, times(1)).findById(bookId);
        verify(bookRepo, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBooksWithNonExistingBook() {
        // Arrange
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBname("Updated Book");
        Author author = new Author();
        author.setAid(1L);
        bookDTO.setAuthor(author);

        when(authorRepo.findById(1L)).thenReturn(Optional.of(new Author()));
        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFound.class, () -> bookService.updateBooks(bookId, bookDTO));
        verify(authorRepo, times(1)).findById(1L);
        verify(bookRepo, times(1)).findById(bookId);
    }

    @Test
    void testDeleteBooksWithExistingBook() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book(bookId, "Book 1", new Author(), null);

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(existingBook));

        // Act
        ResponseEntity<String> response = bookService.deleteBooks(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("delete", response.getBody());
        verify(bookRepo, times(1)).findById(bookId);
        verify(bookRepo, times(1)).deleteById(bookId);
    }

    @Test
    void testDeleteBooksWithNonExistingBook() {
        // Arrange
        Long bookId = 1L;

        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFound.class, () -> bookService.deleteBooks(bookId));
        verify(bookRepo, times(1)).findById(bookId);
    }
}

