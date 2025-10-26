package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Management", description = "APIs for managing authors")
public class AuthorController {
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping
    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get author by ID", description = "Retrieve a specific author by their ID")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        return ResponseEntity.ok(author);
    }
    
    @PostMapping
    @Operation(summary = "Create new author", description = "Create a new author")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update author", description = "Update an existing author")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        
        author.setName(authorDetails.getName());
        Author updatedAuthor = authorRepository.save(author);
        
        return ResponseEntity.ok(updatedAuthor);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author", description = "Delete an author and all their books")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        
        authorRepository.delete(author);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/books")
    @Operation(summary = "Get books by author", description = "Retrieve all books written by a specific author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        
        List<Book> books = bookRepository.findByAuthorId(id);
        return ResponseEntity.ok(books);
    }
    
    @PostMapping("/{id}/books")
    @Operation(summary = "Add book to author", description = "Add a new book to a specific author")
    public ResponseEntity<Book> addBookToAuthor(@PathVariable Long id, @Valid @RequestBody Book book) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
}