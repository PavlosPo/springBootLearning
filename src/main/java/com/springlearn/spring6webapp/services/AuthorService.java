package com.springlearn.spring6webapp.services;

import com.springlearn.spring6webapp.domain.Author;
import com.springlearn.spring6webapp.repositories.AuthorRepository;

public interface AuthorService {

    Iterable<Author> findAll();
}
