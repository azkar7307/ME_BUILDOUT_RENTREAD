package com.crio.rent_read.entity;

import com.crio.rent_read.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "books",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_auther_book_title",
                        columnNames = {"title", "auther"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String auther;
    private String genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status availabilityStatus = Status.AVAILABLE;

    @OneToMany(mappedBy = "book")
    private List<Rental> rentals = new ArrayList<>();
}

