package com.example.novumstudio.service;

import com.example.novumstudio.model.Author;
import com.example.novumstudio.model.Book;
import com.example.novumstudio.repository.AuthorRepository;
import com.example.novumstudio.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    public Book save(Book book) {
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + book.getAuthor().getId()));
        book.setAuthor(author);
        return bookRepository.save(book);
    }
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
