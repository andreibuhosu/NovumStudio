package com.example.novumstudio.controller;

import com.example.novumstudio.model.Author;
import com.example.novumstudio.model.Book;
import com.example.novumstudio.service.AuthorService;
import com.example.novumstudio.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        Author author = authorService.findById(book.getAuthor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + book.getAuthor().getId()));
        book.setAuthor(author);
        return ResponseEntity.ok(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        if (!Objects.equals(id, book.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Author author = authorService.findById(book.getAuthor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + book.getAuthor().getId()));
        book.setAuthor(author);
        return ResponseEntity.ok(bookService.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
