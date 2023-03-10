package com.example.novumstudio.service;

import com.example.novumstudio.model.Author;
import com.example.novumstudio.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
    public Author save(Author author) {
        return authorRepository.save(author);
    }
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
