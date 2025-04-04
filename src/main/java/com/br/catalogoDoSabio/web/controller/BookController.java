package com.br.catalogoDoSabio.web.controller;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.application.service.BookService;
import com.br.catalogoDoSabio.application.service.RecentBooksService;
import com.br.catalogoDoSabio.application.service.impl.BookServiceImpl;
import com.br.catalogoDoSabio.application.service.impl.RecentBooksServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@Tag(name = "Book", description = "Book operations")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RecentBooksService recentBooksService;

    @GetMapping
    @Operation(summary = "Find all books")
    public ResponseEntity<Page<BookDTO>> getAllBooks(Pageable pageable) {
        log.info("New request received at getAllBooks");
        return ResponseEntity.ok(bookService.findAllBooks(pageable));

    }

    @GetMapping("/{id}")
    @Operation(summary = "Find book by id")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        log.info("New request received at getBookById, id: {}", id);
        BookDTO book = bookService.getBookById(id);
        log.info("Response at getBookById: {}", book);
        recentBooksService.addBookToRecent(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/genre")
    @Operation(summary = "Find book by genre")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@RequestParam String genre) {
        if (genre == null || genre.isBlank()) {
            throw new BadRequestException("Genre parameter is mandatory");
        }
        log.info("New request received at getBookByGenre, genre: {}", genre);
        List<BookDTO> books = bookService.getBooksByGenre(genre);
        log.info("Response at getBookByGenre: {}", books);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author")
    @Operation(summary = "Find book by author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author) {
        if (author == null || author.isBlank()) {
            throw new BadRequestException("Author parameter is mandatory");
        }
        log.info("New request received at getBookByAuthor, author: {}", author);
        List<BookDTO> books = bookService.getBooksByAuthor(author);
        log.info("Response at getBookByAuthor: {}", books);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/recent")
    @Operation(summary = "Find books recent")
    public List<BookDTO> getRecentBooks() {
        log.info("New request received at getRecentBooks");
        return recentBooksService.getRecentBooks();
    }
}
