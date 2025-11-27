package com.crio.rent_read.service.impl;

import com.crio.rent_read.dto.response.RentalResponse;
import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.enums.Status;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ValidationServiceImpl validationService;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional
    public RentalResponse rentBook(Long userId, Long bookId) {

        Book book = validationService.validateAndGetAvailableBook(bookId);
        AppUser user = validationService.validateAndGetUser(userId);

        validationService.validateNotAlreadyRented(userId, bookId);

        validationService.validateRentalEligiblity(userId);

        Rental rental = Rental.builder()
            .book(book)
            .user(user)
            .rentalDate(LocalDateTime.now())
            .returnDate(null)
            .build();

        user.getRentals().add(rental);
        book.getRentals().add(rental);
        book.setAvailabilityStatus(Status.NOT_AVAILABLE); // auto persisted updae while dirty checking
        rentalRepository.save(rental);
        log.info("User '{}' successfully rented a book '{}'", userId, bookId);

        return modelMapper.map(rental, RentalResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalResponse> getUserActiveRentals(Long userId) {
        List<Rental> activerRentals = rentalRepository.findAllUsersActiveRentals(userId);
        log.info("active rented Book list fetched from db", userId);
        return activerRentals.stream()
            .map(r -> modelMapper.map(r, RentalResponse.class))
            .toList();
    }

    @Override
    @Transactional
    public void returnBook(Long rentalId) {
        Rental rental = validationService.validateAndGetRental(rentalId);
        validationService.validateReturnProcess(rental);

        Book book = rental.getBook();
        book.setAvailabilityStatus(Status.AVAILABALE);
        rental.setReturnDate(LocalDateTime.now());
        log.info("Book with Rental '{}' has been successfully returned", rentalId);
    }


}
