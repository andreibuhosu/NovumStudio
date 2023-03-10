package com.example.novumstudio;

import com.example.novumstudio.controller.BookController;
import com.example.novumstudio.model.Author;
import com.example.novumstudio.model.Book;
import com.example.novumstudio.service.AuthorService;
import com.example.novumstudio.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookController bookController;

    private Author author;
    private Book book;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Name1", "Bio1");
        book = new Book(1L, "Title1", author, "Description1", "ISBN1", "Publisher1", LocalDate.now());

        bookList = new ArrayList<>();
        bookList.add(book);
    }

    @Test
    void testFindAll() {
        when(bookService.findAll()).thenReturn(bookList);
        assertThat(bookController.findAll()).isEqualTo(bookList);
    }

    @Test
    void testFindById() {
        when(bookService.findById(1L)).thenReturn(Optional.of(book));
        ResponseEntity<Book> response = bookController.findById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);
    }

    @Test
    void testFindByIdNotFound() {
        when(bookService.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Book> response = bookController.findById(2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSave() {
        Author author = new Author(null, "John", "Doe");
        Book book = new Book(1L, "Title", author, "Description", "ISBN", "Publisher", LocalDate.now());
        Book newBook = new Book(null, "Title2", author, "Description2", "ISBN2", "Publisher2", LocalDate.now());

        when(bookService.save(newBook)).thenReturn(book);
        when(authorService.findById(author.getId())).thenReturn(Optional.of(author));

        ResponseEntity<Book> response = bookController.save(newBook);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);
    }

    @Test
    void testUpdate() {
        Author updatedAuthor = new Author(2L, "John", "Smith");
        Book updatedBook = new Book(1L, "UpdatedTitle1", updatedAuthor, "UpdatedDescription1", "UpdatedISBN1", "UpdatedPublisher1", LocalDate.now());

        when(bookService.save(updatedBook)).thenReturn(updatedBook);
        when(authorService.findById(updatedAuthor.getId())).thenReturn(Optional.of(updatedAuthor));

        ResponseEntity<Book> response = bookController.update(1L, updatedBook);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedBook);
    }


    @Test
    void testUpdateBadRequest() {
        Book updatedBook = new Book(2L, "UpdatedTitle2", author, "UpdatedDescription2", "UpdatedISBN2", "UpdatedPublisher2", LocalDate.now());
        ResponseEntity<Book> response = bookController.update(1L, updatedBook);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteById() {
        doNothing().when(bookService).deleteById(1L);
        ResponseEntity<Void> response = bookController.deleteById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
