package com.br.catalogoDoSabio.domain.repository;

import com.br.catalogoDoSabio.domain.entity.BookDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDAO, Long> {

    List<BookDAO> findByGenre(String genre);

    List<BookDAO> findByAuthor(String author);

}
