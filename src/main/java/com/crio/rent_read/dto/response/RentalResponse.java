package com.crio.rent_read.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private BookResponse book;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
}
