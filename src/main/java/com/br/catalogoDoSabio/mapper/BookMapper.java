package com.br.catalogoDoSabio.mapper;


import com.br.catalogoDoSabio.domain.entity.BookDAO;
import com.br.catalogoDoSabio.application.dto.BookDTO;

public class BookMapper {

    public static BookDTO daoToResponseDto(BookDAO bookDAO) {
        BookDTO dto = new BookDTO();
        dto.setId(bookDAO.getId());
        dto.setTitle(bookDAO.getTitle());
        dto.setDescription(bookDAO.getDescription());
        dto.setGenre(bookDAO.getGenre());
        dto.setAuthor(bookDAO.getAuthor());
        return dto;
    }
}