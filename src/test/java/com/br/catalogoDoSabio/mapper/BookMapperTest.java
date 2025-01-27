package com.br.catalogoDoSabio.mapper;

import com.br.catalogoDoSabio.domain.entity.BookDAO;
import com.br.catalogoDoSabio.application.dto.BookDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookMapperTest {

    @Test
    void testDaoToResponseDto() {
        // Setup: Criando um objeto BookDAO com dados de teste
        BookDAO bookDAO = new BookDAO();
        bookDAO.setId(1L);
        bookDAO.setTitle("Book Title");
        bookDAO.setDescription("Book Description");
        bookDAO.setGenre("Fantasy");
        bookDAO.setAuthor("Author Name");

        // Action: Convertendo o BookDAO para BookDTO
        BookDTO bookDTO = BookMapper.daoToResponseDto(bookDAO);

        // Assert: Verificando se a conversão foi realizada corretamente
        assertNotNull(bookDTO);
        assertEquals(bookDAO.getId(), bookDTO.getId());
        assertEquals(bookDAO.getTitle(), bookDTO.getTitle());
        assertEquals(bookDAO.getDescription(), bookDTO.getDescription());
        assertEquals(bookDAO.getGenre(), bookDTO.getGenre());
        assertEquals(bookDAO.getAuthor(), bookDTO.getAuthor());
    }
}
