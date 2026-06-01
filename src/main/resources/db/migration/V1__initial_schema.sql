-- V1__create_users_books_rentals_tables.sql

-- =========================
-- USERS TABLE
-- =========================
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,

                       email VARCHAR(255) UNIQUE,

                       role VARCHAR(50) NOT NULL DEFAULT 'USER',

                       first_name VARCHAR(255),

                       last_name VARCHAR(255),

                       password VARCHAR(255)
);

-- =========================
-- BOOKS TABLE
-- =========================
CREATE TABLE books (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,

                       title VARCHAR(255),

                       author VARCHAR(255),

                       genre VARCHAR(255),

                       availability_status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',

                       CONSTRAINT unique_author_book_title
                           UNIQUE (title, author)
);

-- =========================
-- RENTALS TABLE
-- =========================
CREATE TABLE rentals (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,

                         user_id BIGINT,

                         book_id BIGINT,

                         rental_date TIMESTAMP NOT NULL,

                         return_date TIMESTAMP NULL,

                         CONSTRAINT fk_rentals_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,

                         CONSTRAINT fk_rentals_book
                             FOREIGN KEY (book_id)
                                 REFERENCES books(id)
                                 ON DELETE CASCADE
);

-- =========================
-- INDEXES
-- =========================
CREATE INDEX idx_rentals_user_id
    ON rentals(user_id);

CREATE INDEX idx_rentals_book_id
    ON rentals(book_id);

CREATE INDEX idx_books_availability_status
    ON books(availability_status);