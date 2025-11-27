package com.crio.rent_read.repository;

import java.util.List;
import com.crio.rent_read.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("""
    SELECT 
        CASE WHEN COUNT(r) > 0 
            THEN TRUE 
            ELSE FALSE 
        END
    FROM Rental r
    WHERE r.user.id = :userId
    AND r.book.id = :bookId
    AND r.returnDate IS NULL
    """)
    boolean isAlreadyRented(long userId, long bookId);

    // @Query("""
    //     SELECT COUNT(r)
    //     FROM Rental r
    //     WHERE r.user.id = :userId
    //       AND r.returnDate IS NULL
    //       AND r.book.availabilityStatus = com.crio.rent_read.entity.enums.Status.NOT_AVAILABLE
    //     """)
    // long countActiveRentals(Long userId);

    @Query("""
        SELECT r
        FROM Rental r
        WHERE r.user.id = :userId
          AND r.returnDate IS NULL
          AND r.book.availabilityStatus = com.crio.rent_read.entity.enums.Status.NOT_AVAILABLE
        """)
    List<Rental> findAllUsersActiveRentals(Long userId);
}