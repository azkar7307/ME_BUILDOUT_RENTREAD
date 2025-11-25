package com.crio.rent_read.repository;

import com.crio.rent_read.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}