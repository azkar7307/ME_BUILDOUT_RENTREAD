package com.crio.rent_read.service;

import java.util.List;
import com.crio.rent_read.dto.response.RentalResponse;

public interface RentalService {
    
    RentalResponse rentBook(Long userId, Long bookId);

    List<RentalResponse> getUserActiveRentals(Long userId);

    void returnBook(Long rentalId);


}
