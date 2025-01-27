package com.br.catalogoDoSabio.application.service;

import com.br.catalogoDoSabio.application.dto.BookDTO;

import java.util.List;

public interface RecentBooksService {

    List<BookDTO> getRecentBooks();
}
