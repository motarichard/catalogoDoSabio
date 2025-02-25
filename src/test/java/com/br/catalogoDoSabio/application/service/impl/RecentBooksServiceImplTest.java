package com.br.catalogoDoSabio.application.service.impl;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecentBooksServiceImplTest {

    @Mock
    private RedisTemplate<String, BookDTO> redisTemplate;

    @Mock
    private ListOperations<String, BookDTO> listOperations;

    @InjectMocks
    private RecentBooksServiceImpl recentBooksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
    }

    @Test
    void testAddBookToRecent() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");
        bookDTO.setGenre("Test Genre");

        recentBooksService.addBookToRecent(bookDTO);

        verify(listOperations, times(1)).leftPush("recent_books", bookDTO);
        verify(listOperations, times(1)).trim("recent_books", 0, 9);
    }

    @Test
    void testGetRecentBooks() {
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Recent Book 1");
        bookDTO1.setAuthor("Author 1");
        bookDTO1.setGenre("Genre 1");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Recent Book 2");
        bookDTO2.setAuthor("Author 2");
        bookDTO2.setGenre("Genre 2");

        List<BookDTO> recentBooks = List.of(bookDTO1, bookDTO2);

        when(listOperations.range("recent_books", 0, -1)).thenReturn(recentBooks);

        List<BookDTO> result = recentBooksService.getRecentBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(bookDTO1, result.get(0));
        assertEquals(bookDTO2, result.get(1));

        verify(listOperations, times(1)).range("recent_books", 0, -1);
    }
}
