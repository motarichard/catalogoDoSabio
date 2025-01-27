package com.br.catalogoDoSabio.web.handler;

import com.br.catalogoDoSabio.application.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class DefaultApplicationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(NotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String trace = getTraceId();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setTitle("Not Found");
        errorDTO.setDescription(e.getMessage());
        errorDTO.setCode(String.valueOf(status.value()));
        errorDTO.setTraceId(trace);
        logErrorResponse(errorDTO);
        return new ResponseEntity<>(errorDTO, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequest(BadRequestException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String trace = getTraceId();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setTitle("Bad Request");
        errorDTO.setDescription(e.getMessage());
        errorDTO.setCode(String.valueOf(status.value()));
        errorDTO.setTraceId(trace);
        logErrorResponse(errorDTO);
        return new ResponseEntity<>(errorDTO, status);
    }

    private String getTraceId() {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return contextMap != null ? contextMap.get("traceId") : null;
    }

    private void logErrorResponse(ErrorDTO errorDTO) {
        log.error("Response error: {}", errorDTO);
    }
}