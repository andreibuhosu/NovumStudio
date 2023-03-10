package com.example.novumstudio;

import com.example.novumstudio.controller.AuthorController;
import com.example.novumstudio.model.Author;
import com.example.novumstudio.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private Author author;
    private List<Author> authorList;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Name1", "Bio1");
        authorList = new ArrayList<>();
        authorList.add(author);
    }

    @Test
    void testFindAll() {
        when(authorService.findAll()).thenReturn(authorList);
        assertThat(authorController.findAll()).isEqualTo(authorList);
    }

    @Test
    void testFindById() {
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        ResponseEntity<Author> response = authorController.findById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(author);
    }

    @Test
    void testFindByIdNotFound() {
        when(authorService.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Author> response = authorController.findById(2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSave() {
        Author newAuthor = new Author(null, "Name2", "Bio2");
        when(authorService.save(newAuthor)).thenReturn(author);
        ResponseEntity<Author> response = authorController.save(newAuthor);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(author);
    }

    @Test
    void testUpdate() {
        Author updatedAuthor = new Author(1L, "UpdatedName1", "UpdatedBio1");
        when(authorService.save(updatedAuthor)).thenReturn(updatedAuthor);
        ResponseEntity<Author> response = authorController.update(1L, updatedAuthor);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedAuthor);
    }

    @Test
    void testUpdateBadRequest() {
        Author updatedAuthor = new Author(2L, "UpdatedName2", "UpdatedBio2");
        ResponseEntity<Author> response = authorController.update(1L, updatedAuthor);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteById() {
        doNothing().when(authorService).deleteById(1L);
        ResponseEntity<Void> response = authorController.deleteById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}

