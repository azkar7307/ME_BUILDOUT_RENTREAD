package com.crio.rent_read.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> registerBook(@RequestBody BookRequest bookRequest) {
        log.info("Request received to register new book");
        BookResponse bookResponse = bookService.registerBook(bookRequest);
        return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookById(
        @PathVariable Long id, 
        @RequestBody BookRequest bookUpdateRequest
    ) {
        log.info("Request received to update book '{}'", id);
        BookResponse bookResponse = bookService.updateBookById(id, bookUpdateRequest);
        return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        log.info("Request received to delete book '{}'", id);
        bookService.deleteBookById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
