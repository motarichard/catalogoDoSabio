package com.br.catalogoDoSabio.web.controller;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.application.service.impl.BookServiceImpl;
import com.br.catalogoDoSabio.application.service.impl.RecentBooksServiceImpl;
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
        PageRequest pageable = PageRequest.of(0, 10);
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", "Author", "Genre", "Description"));
        Page<BookDTO> pageBooks = new PageImpl<>(books);

        when(bookService.findAllBooks(pageable)).thenReturn(pageBooks);

        ResponseEntity<Page<BookDTO>> response = bookController.getAllBooks(pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(bookService, times(1)).findAllBooks(pageable);
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        BookDTO book = new BookDTO(bookId, "Book Title", "Author", "Genre", "Description");

        when(bookService.getBookById(bookId)).thenReturn(book);

        ResponseEntity<BookDTO> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
        verify(recentBooksService, times(1)).addBookToRecent(book);
    }

    @Test
    void testGetBooksByGenre() {
        String genre = "Science Fiction";
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", "Author", genre, "Description"));

        when(bookService.getBooksByGenre(genre)).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByGenre(genre);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByGenre(genre);
    }

    @Test
    void testGetBooksByAuthor() {
        String author = "Author Name";
        List<BookDTO> books = List.of(new BookDTO(1L, "Book Title", author, "Genre", "Description"));

        when(bookService.getBooksByAuthor(author)).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAuthor(author);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByAuthor(author);
    }

    @Test
    void testGetRecentBooks() {
        List<BookDTO> recentBooks = List.of(new BookDTO(1L, "Book Title", "Author", "Genre", "Description"));

        when(recentBooksService.getRecentBooks()).thenReturn(recentBooks);

        List<BookDTO> response = bookController.getRecentBooks();

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(recentBooksService, times(1)).getRecentBooks();
    }

    @Test
    void testGetBooksByGenreWithBadRequest() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            bookController.getBooksByGenre("");
        });
        assertEquals("Genre parameter is mandatory", exception.getMessage());
    }

    @Test
    void testGetBooksByAuthorWithBadRequest() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            bookController.getBooksByAuthor("");
        });
        assertEquals("Author parameter is mandatory", exception.getMessage());
    }
}
