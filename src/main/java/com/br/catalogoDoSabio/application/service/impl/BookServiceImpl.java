package com.br.catalogoDoSabio.application.service.impl;

import com.br.catalogoDoSabio.domain.entity.Book;
import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.domain.repository.BookRepository;
import com.br.catalogoDoSabio.mapper.BookMapper;
import com.br.catalogoDoSabio.application.service.BookService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Override
    @Cacheable(value = "books", keyGenerator = "keyGenerator", unless = "#result == null or #result.empty")
    public Page<BookDTO> findAllBooks(Pageable pageable) {
        Page<Book> daoPage = repository.findAll(pageable);
        List<BookDTO> responseDTO = new ArrayList<>();
        daoPage.forEach(bookDAO -> responseDTO.add(BookMapper.daoToResponseDto(bookDAO)));
        return new PageImpl<>(responseDTO, pageable, daoPage.getTotalElements());
    }

    @Override
    @Cacheable(value = "bookById", key = "#id", unless = "#result == null")
    public BookDTO getBookById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book id not found"));
        return BookMapper.daoToResponseDto(book);
    }

    @Override
    @Cacheable(value = "booksByGenre", key = "#genre", unless = "#result == null or #result.empty")
    public List<BookDTO> getBooksByGenre(String genre) {
        List<Book> daoList = repository.findByGenre(genre);
        verifyListNotEmpty(daoList);
        List<BookDTO> responseDTO = new ArrayList<>();
        daoList.forEach(bookDAO -> responseDTO.add(BookMapper.daoToResponseDto(bookDAO)));
        return responseDTO;
    }

    @Override
    @Cacheable(value = "booksByAuthor", key = "#author", unless = "#result == null or #result.empty")
    public List<BookDTO> getBooksByAuthor(String author) {
        List<Book> daoList = repository.findByAuthor(author);
        verifyListNotEmpty(daoList);
        List<BookDTO> responseDTO = new ArrayList<>();
        daoList.forEach(bookDAO -> responseDTO.add(BookMapper.daoToResponseDto(bookDAO)));
        return responseDTO;
    }

    private void verifyListNotEmpty(List<Book> books) {
        if (books.isEmpty()) {
            throw new NotFoundException("Book id not found");
        }
    }
}
