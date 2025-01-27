package com.br.catalogoDoSabio.application.service;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<BookDTO> findAllBooks(Pageable pageable);

    BookDTO getBookById(Long id);

    List<BookDTO> getBooksByGenre(String genre);

    List<BookDTO> getBooksByAuthor(String author);

}
