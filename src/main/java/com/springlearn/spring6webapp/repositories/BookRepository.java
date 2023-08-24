package com.springlearn.spring6webapp.repositories;


import com.springlearn.spring6webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
