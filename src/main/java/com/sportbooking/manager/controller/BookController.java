package com.sportbooking.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportbooking.manager.dto.BookRequest;
import com.sportbooking.manager.model.Book;
import com.sportbooking.manager.service.BookService;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
   
    @PostMapping
    public ResponseEntity<Book> createBook(@Validated @RequestBody BookRequest request) {
        Book saved = bookService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Validated @RequestBody BookRequest request) {
        Book updated = bookService.update(id, request);

        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book deleted = bookService.deleteById(id);

        return ResponseEntity.ok().body(deleted);
    }

    @GetMapping("/ids")
    public ResponseEntity<Map<Long, String>> getAllBookIdsAndTitles() {
        Map<Long, String> idsAndTitles = bookService.findAllIdsAndTitles();

        return ResponseEntity.ok().body(idsAndTitles);
    }
}
