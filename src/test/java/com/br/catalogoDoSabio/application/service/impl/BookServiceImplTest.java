package com.br.catalogoDoSabio.application.service.impl;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.domain.entity.BookDAO;
import com.br.catalogoDoSabio.domain.repository.BookRepository;
import com.br.catalogoDoSabio.mapper.BookMapper;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDAO bookDAO;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO(1L, "Book Title", "Author Name", "Genre Name", "Book Description");
        bookDTO = new BookDTO(1L, "Book Title", "Author Name", "Genre Name", "Book Description");

    }

    @Test
    void testFindAllBooks() {
        try (MockedStatic<BookMapper> mockedStatic = mockStatic(BookMapper.class)) {
            mockedStatic.when(() -> BookMapper.daoToResponseDto(bookDAO)).thenReturn(bookDTO);

            Pageable pageable = PageRequest.of(0, 10);
            Page<BookDAO> pageMock = new PageImpl<>(Arrays.asList(bookDAO), pageable, 1);
            when(bookRepository.findAll(pageable)).thenReturn(pageMock);

            Page<BookDTO> result = bookService.findAllBooks(pageable);

            assertEquals(1, result.getTotalElements());
            assertEquals(bookDTO, result.getContent().get(0));
            verify(bookRepository, times(1)).findAll(pageable);
        }
    }

    @Test
    void testGetBookById_WhenBookExists() {
        try (MockedStatic<BookMapper> mockedStatic = mockStatic(BookMapper.class)) {
            mockedStatic.when(() -> BookMapper.daoToResponseDto(bookDAO)).thenReturn(bookDTO);

            Long bookId = 1L;
            when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookDAO));

            BookDTO result = bookService.getBookById(bookId);

            assertNotNull(result);
            assertEquals(bookDTO, result);
            verify(bookRepository, times(1)).findById(bookId);
        }
    }

    @Test
    void testGetBookById_WhenBookDoesNotExist() {
        try (MockedStatic<BookMapper> mockedStatic = mockStatic(BookMapper.class)) {
            mockedStatic.when(() -> BookMapper.daoToResponseDto(bookDAO)).thenReturn(bookDTO);

            Long bookId = 999L;
            when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.getBookById(bookId));
            assertEquals("Book id not found", exception.getMessage());
            verify(bookRepository, times(1)).findById(bookId);
        }
    }

    @Test
    void testGetBooksByGenre() {
        try (MockedStatic<BookMapper> mockedStatic = mockStatic(BookMapper.class)) {
            mockedStatic.when(() -> BookMapper.daoToResponseDto(bookDAO)).thenReturn(bookDTO);

            String genre = "Genre Name";
            List<BookDAO> daoList = Arrays.asList(bookDAO);
            when(bookRepository.findByGenre(genre)).thenReturn(daoList);

            List<BookDTO> result = bookService.getBooksByGenre(genre);

            assertEquals(1, result.size());
            assertEquals(bookDTO, result.get(0));
            verify(bookRepository, times(1)).findByGenre(genre);
        }
    }

    @Test
    void testGetBooksByAuthor() {
        try (MockedStatic<BookMapper> mockedStatic = mockStatic(BookMapper.class)) {
            mockedStatic.when(() -> BookMapper.daoToResponseDto(bookDAO)).thenReturn(bookDTO);

            String author = "Author Name";
            List<BookDAO> daoList = Arrays.asList(bookDAO);
            when(bookRepository.findByAuthor(author)).thenReturn(daoList);

            List<BookDTO> result = bookService.getBooksByAuthor(author);

            assertEquals(1, result.size());
            assertEquals(bookDTO, result.get(0));
            verify(bookRepository, times(1)).findByAuthor(author);
        }
    }
}

