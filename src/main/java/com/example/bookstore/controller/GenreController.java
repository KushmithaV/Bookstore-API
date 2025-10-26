package com.example.bookstore.controller;

import com.example.bookstore.entity.Genre;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.GenreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "Genre Management", description = "APIs for managing genres")
public class GenreController {
    
    @Autowired
    private GenreRepository genreRepository;
    
    @GetMapping
    @Operation(summary = "Get all genres", description = "Retrieve a list of all genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return ResponseEntity.ok(genres);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get genre by ID", description = "Retrieve a specific genre by its ID")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
        return ResponseEntity.ok(genre);
    }
    
    @PostMapping
    @Operation(summary = "Create new genre", description = "Create a new genre")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update genre", description = "Update an existing genre")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @Valid @RequestBody Genre genreDetails) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
        
        genre.setName(genreDetails.getName());
        Genre updatedGenre = genreRepository.save(genre);
        
        return ResponseEntity.ok(updatedGenre);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete genre", description = "Delete a genre")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
        
        genreRepository.delete(genre);
        return ResponseEntity.noContent().build();
    }
}
