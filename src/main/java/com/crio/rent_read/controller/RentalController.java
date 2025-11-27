package com.crio.rent_read.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.crio.rent_read.dto.response.RentalResponse;
import com.crio.rent_read.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
    
    private final RentalService rentalService;

    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<RentalResponse> rentBook(
        @PathVariable Long userId,
        @PathVariable Long bookId
    ) {
        log.info("Process the rental request: User '{}' is requesting to rent Book '{}'", userId, bookId);
        RentalResponse response = rentalService.rentBook(userId, bookId);
        return new ResponseEntity<RentalResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/active-rentals/users/{userId}")
    public ResponseEntity<List<RentalResponse>> getUserActiveRentals(@PathVariable Long userId) {
        log.info(
            "Process the the active rental request:" 
            + "User '{}' is requesting to get active rented Book list", userId
        );
        List<RentalResponse> responses = rentalService.getUserActiveRentals(userId);
        log.info("User '{}' fetched active rented Book list", userId);
        return new ResponseEntity<List<RentalResponse>>(responses, HttpStatus.OK);
    }

    @PutMapping("/{rental_id}")
    public ResponseEntity<Void> returnBook(@PathVariable("rental_id") Long rentalId) {
        log.info(
            "Process the return request: Book with Rental '{}' is being requested to be returned",
             rentalId
        );
        rentalService.returnBook(rentalId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
