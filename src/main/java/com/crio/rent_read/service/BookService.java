package com.crio.rent_read.service;

import java.util.List;
import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;

public interface BookService {
    
    BookResponse registerBook(BookRequest bookRequest);

    BookResponse updateBookById(Long id, BookRequest updateBookRequest);
    
    void deleteBookById(Long id);

    List<BookResponse> getAllAvalilableBooks();
}
