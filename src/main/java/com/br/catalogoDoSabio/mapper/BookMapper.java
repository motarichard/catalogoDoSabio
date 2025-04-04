package com.br.catalogoDoSabio.mapper;


import com.br.catalogoDoSabio.domain.entity.Book;
import com.br.catalogoDoSabio.application.dto.BookDTO;

public class BookMapper {

    public static BookDTO daoToResponseDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setGenre(book.getGenre());
        dto.setAuthor(book.getAuthor());
        return dto;
    }
}