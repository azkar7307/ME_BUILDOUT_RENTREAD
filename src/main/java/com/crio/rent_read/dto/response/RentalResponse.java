package com.crio.rent_read.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import com.crio.rent_read.entity.Book;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private Book book;
    private LocalDateTime rentedAt;
    private LocalDateTime returnDate;
}
