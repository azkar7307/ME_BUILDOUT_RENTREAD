package com.crio.rent_read.dto.request;

import com.crio.rent_read.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "Book title is required")
    private String title;

    @NotBlank(message = "Auther name is required")
    private String author;

    @NotBlank(message = "Genre is required")
    private String genre;
    
    private Status availabilityStatus;
}