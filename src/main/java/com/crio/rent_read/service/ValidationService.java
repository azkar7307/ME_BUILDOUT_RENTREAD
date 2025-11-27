package com.crio.rent_read.service;

import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;

public interface ValidationService {
    
    void validateUserExistsByEmail(String email);
    
    Book validateAndGetBook(Long id);
    
    Book validateAndGetAvailableBook(Long bookId);
    
    AppUser validateAndGetUser(Long id);
    
    void validateNotAlreadyRented(Long userId, Long bookId);
    
    void validateRentalEligiblity(Long userId);
    
    Rental validateAndGetRental(Long rentalId);
    
    void validateReturnProcess(Rental rental);

    AppUser validateAndGetUserByEmail(String email) ;
}
