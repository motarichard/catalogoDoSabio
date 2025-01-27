package com.br.catalogoDoSabio.application.service.impl;

import com.br.catalogoDoSabio.application.dto.BookDTO;
import com.br.catalogoDoSabio.application.service.RecentBooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecentBooksServiceImpl implements RecentBooksService {

    private static final String RECENT_BOOKS_KEY = "recent_books";

    @Autowired
    private RedisTemplate<String, BookDTO> redisTemplate;

    public void addBookToRecent(BookDTO bookDTO) {
        log.info("Add the book to Redis (in the 'recent_books' key)");
        redisTemplate.opsForList().leftPush(RECENT_BOOKS_KEY, bookDTO);
        redisTemplate.opsForList().trim(RECENT_BOOKS_KEY, 0, 9);
    }

    @Override
    public List<BookDTO> getRecentBooks() {
        log.info("Retrieves books from key 'recent_books' in Redis");
        return redisTemplate.opsForList().range(RECENT_BOOKS_KEY, 0, -1);
    }
}
