package com.crio.rent_read.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.enums.Status;
import com.crio.rent_read.exception.BadRequestException;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.service.impl.ValidationServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ValidationServiceImplTest {

    @Mock
    private AppUserRepository userRepository;
    
    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;
    
    @Test
    void validateRentalEligiblity_Return_Void() {

        List<Rental> rentals = List.of(Rental.builder().build());
        // setup
        when(rentalRepository.findAllUsersActiveRentals(anyLong())).thenReturn(rentals);

        // execute
        validationServiceImpl.validateRentalEligiblity(1L);

        // verify
        verify(rentalRepository, times(1)).findAllUsersActiveRentals(anyLong());
    }

    @Test
    void validateRentalEligiblity_Throw_ConflictException() {

        List<Rental> rentals = List.of(
            Rental.builder().build(),
            Rental.builder().build(),
            Rental.builder().build()
        );

        // setup
        when(rentalRepository.findAllUsersActiveRentals(anyLong())).thenReturn(rentals);

        // execute
        assertThrows(
            BadRequestException.class,
            () -> validationServiceImpl.validateRentalEligiblity(1L)
        );

        // verify
        verify(rentalRepository, times(1)).findAllUsersActiveRentals(anyLong());

    }
    
    @Test
    void validateReturnProcess_Return_Void() {
        // setup
        Book book = new Book();
        book.setAvailabilityStatus(Status.NOT_AVAILABLE);

        Rental rental = Rental.builder().book(book).returnDate(null).build();

        // execute
        validationServiceImpl.validateReturnProcess(rental);

    }

    @Test
    void validateReturnProcess_Status_Available_Throw_BadRequestException() {
        // setup
        Book book = new Book();
        book.setAvailabilityStatus(Status.AVAILABLE);

        Rental rental = Rental.builder().id(1L).book(book).returnDate(null).build();

        // execute
        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> validationServiceImpl.validateReturnProcess(rental)
        );

        String expectedMsg =  "Return process failed. Video already marked available for rental '" 
            + rental.getId() 
            + "'";
        
        assertEquals(expectedMsg, ex.getMessage());
    }

    @Test
    void validateReturnProcess_ReturnDate_Valid_Throw_BadRequest_Exception() {
        // setup
        Book book = new Book();
        book.setAvailabilityStatus(Status.NOT_AVAILABLE);
        LocalDateTime returnDate = LocalDateTime.now();
        Rental rental = Rental.builder().id(1L).book(book).returnDate(returnDate).build();

        // execute
        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> validationServiceImpl.validateReturnProcess(rental)
        );

        String expectedMsg = "Return process failed. The rental '" 
            + rental.getId() 
            + "' has already been returned on '"
            + rental.getReturnDate()
            +"'";
        
        assertEquals(expectedMsg, ex.getMessage());
    }
}
