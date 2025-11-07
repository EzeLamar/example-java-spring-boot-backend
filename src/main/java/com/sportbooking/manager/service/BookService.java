package com.sportbooking.manager.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sportbooking.manager.dto.BookRequest;
import com.sportbooking.manager.model.Book;
import com.sportbooking.manager.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book create(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYearPublished(request.getYearPublished());

        return bookRepository.save(book);
    }

    public Book update(Long id, BookRequest request) {
        Book existingBook = findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setYearPublished(request.getYearPublished());
        return bookRepository.save(existingBook);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book deleteById(Long id) {
        Book existingBook = findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.deleteById(id);

        return existingBook;
    }

    public Map<Long, String> findAllIdsAndTitles() {
        return bookRepository.findAll().stream()
            .collect(Collectors.toMap(Book::getId, Book::getTitle));
    }
}
