package com.crio.rent_read.service.impl;

import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.Status;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ValidationServiceImpl validationService;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse registerBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        Book savedBook = bookRepository.save(book);
        log.info("New book '{}' registered successfully", book.getId());
        return modelMapper.map(savedBook, BookResponse.class);
    }

    @Override
    @Transactional
    public BookResponse updateBookById(Long id, BookRequest updateBookRequest) {
        Book book = validationService.validateAndGetBook(id);
        book.setAuthor(updateBookRequest.getAuthor());
        book.setTitle(updateBookRequest.getTitle());
        book.setGenre(updateBookRequest.getGenre());
        book.setAvailabilityStatus(updateBookRequest.getAvailabilityStatus());
        log.info("Book '{}' updated successfully", id);
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public void deleteBookById(Long id) {
        Book book = validationService.validateAndGetBook(id);
        bookRepository.delete(book);
        log.info("Book '{}' deleted successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllAvalilableBooks() {
        List<Book> books = bookRepository.findByAvailabilityStatus(Status.AVAILABLE);
        return books.stream()
            .map(b -> modelMapper.map(b, BookResponse.class))
            .toList();
    }
}
