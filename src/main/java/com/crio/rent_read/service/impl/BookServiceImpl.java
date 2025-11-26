package com.crio.rent_read.service.impl;

import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.service.BookService;

public class BookServiceImpl implements BookService {
 
    @Override
    public BookResponse registerBook(BookRequest bookRequest) {
        return null;
    }


    @Override
    public BookResponse updateBookById(Long id, BookRequest updateBookRequest) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {}
    
}
