package com.springlearn.spring6webapp.services;

import com.springlearn.spring6webapp.domain.Book;

public interface BookService {

    Iterable<Book> findAll();
}
