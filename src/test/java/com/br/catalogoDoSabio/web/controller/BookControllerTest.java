package com.br.catalogoDoSabio.web.controller;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.application.service.impl.BookServiceImpl;
import com.br.catalogoDoSabio.application.service.impl.RecentBooksServiceImpl;
import com.br.catalogoDoSabio.web.controller.BookController;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookControllerTest {

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private RecentBooksServiceImpl recentBooksService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        // Setup
        PageRequest pageable = PageRequest.of(0, 10);
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", "Author", "Genre", "Description"));
        Page<BookDTO> pageBooks = new PageImpl<>(books);

        when(bookService.findAllBooks(pageable)).thenReturn(pageBooks);

        // Action
        ResponseEntity<Page<BookDTO>> response = bookController.getAllBooks(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(bookService, times(1)).findAllBooks(pageable);
    }

    @Test
    void testGetBookById() {
        // Setup
        Long bookId = 1L;
        BookDTO book = new BookDTO(bookId, "Book Title", "Author", "Genre", "Description");

        when(bookService.getBookById(bookId)).thenReturn(book);

        // Action
        ResponseEntity<BookDTO> response = bookController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
        verify(recentBooksService, times(1)).addBookToRecent(book);
    }

    @Test
    void testGetBooksByGenre() {
        // Setup
        String genre = "Science Fiction";
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", "Author", genre, "Description"));

        when(bookService.getBooksByGenre(genre)).thenReturn(books);

        // Action
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByGenre(genre);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByGenre(genre);
    }

    @Test
    void testGetBooksByAuthor() {
        // Setup
        String author = "Author Name";
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", author, "Genre", "Description"));

        when(bookService.getBooksByAuthor(author)).thenReturn(books);

        // Action
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAuthor(author);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByAuthor(author);
    }

    @Test
    void testGetRecentBooks() {
        // Setup
        List<BookDTO> recentBooks = List.of(new BookDTO(1L, "Book Title", "Author", "Genre", "Description"));

        when(recentBooksService.getRecentBooks()).thenReturn(recentBooks);

        // Action
        List<BookDTO> response = bookController.getRecentBooks();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        verify(recentBooksService, times(1)).getRecentBooks();
    }

    @Test
    void testGetBooksByGenreWithBadRequest() {
        // Action & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            bookController.getBooksByGenre("");
        });
        assertEquals("Genre parameter is mandatory", exception.getMessage());
    }

    @Test
    void testGetBooksByAuthorWithBadRequest() {
        // Action & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            bookController.getBooksByAuthor("");
        });
        assertEquals("Author parameter is mandatory", exception.getMessage());
    }
}
