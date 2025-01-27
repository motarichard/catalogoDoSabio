package com.br.catalogoDoSabio.web.handler;

import com.br.catalogoDoSabio.application.dto.ErrorDTO;
import com.br.catalogoDoSabio.web.handler.DefaultApplicationExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DefaultApplicationExceptionHandlerTest {
    private DefaultApplicationExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new DefaultApplicationExceptionHandler();
    }

    @Test
    void shouldHandleNotFoundException() {
        NotFoundException exception = new NotFoundException("Resource not found");
        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<ErrorDTO> response = exceptionHandler.handleNotFound(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Not Found");
        assertThat(response.getBody().getDescription()).isEqualTo("Resource not found");
        assertThat(response.getBody().getCode()).isEqualTo("404");
    }

    @Test
    void shouldHandleBadRequestException() {
        BadRequestException exception = new BadRequestException("Bad request error");
        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<ErrorDTO> response = exceptionHandler.handleBadRequest(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Bad Request");
        assertThat(response.getBody().getDescription()).isEqualTo("Bad request error");
        assertThat(response.getBody().getCode()).isEqualTo("400");
    }
}

