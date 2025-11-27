package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.enums.Status;
import com.crio.rent_read.exception.BadRequestException;
import com.crio.rent_read.exception.ConflictException;
import com.crio.rent_read.exception.EntityNotFoundException;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.service.ValidationService;
import com.crio.rent_read.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    
    private final AppUserRepository userRepository;
    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;

    @Override
    @Transactional(readOnly = true)
    public void validateUserExistsByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new ConflictException(Util.mask(email), "User");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book validateAndGetBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Book"));
    }

    @Override
    @Transactional(readOnly = true)
    public Book validateAndGetAvailableBook(Long bookId) {
        Book book = bookRepository.findByIdAndAvailabilityStatus(bookId, Status.AVAILABLE)
            .orElseThrow(
                () -> new EntityNotFoundException("Book '" + bookId + "' not availabe for rent")
            );
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser validateAndGetUser(Long id) {
        AppUser user = userRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(id, "User")
            );
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public void validateNotAlreadyRented(Long userId, Long bookId) {
        if (rentalRepository.isAlreadyRented(userId, bookId)) {
            throw new BadRequestException("User '" + userId + "' already rented the book '" + bookId + "'");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validateRentalEligiblity(Long userId) {
        // count active rentals
        if (rentalRepository.findAllUsersActiveRentals(userId).size() >= 2) {
            throw new ConflictException("User '" + userId + "' " + "exceed maximum rental count");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Rental validateAndGetRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
            () -> new EntityNotFoundException(rentalId, "Rental")
        );
        return rental;
    }

    @Override
    @Transactional(readOnly = true)
    public void validateReturnProcess(Rental rental) {
        if (rental.getBook().getAvailabilityStatus().name().equals(Status.AVAILABLE.name())) {
            throw new BadRequestException(
                "Return process failed. Video already marked available for rental '"
                + rental.getId()
                + "'"
            );
        }

        if (rental.getReturnDate() != null ) {
            throw new BadRequestException(
                "Return process failed. The rental '" 
                + rental.getId() 
                + "' has already been returned on '"
                + rental.getReturnDate()
                +"'"
            );
        }

    }
}
