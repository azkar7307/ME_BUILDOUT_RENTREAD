package com.crio.rent_read.repository;

import java.util.List;
import java.util.Optional;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAvailabilityStatus(Status availabilityStatus);
    
    Optional<Book> findByIdAndAvailabilityStatus(Long id, Status status);
}