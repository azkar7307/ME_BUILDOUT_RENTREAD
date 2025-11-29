# 📚 Book Rental System — Problem Statement

Develop a **RESTful API** using **Spring Boot** to manage an online book rental system. Persist data using  **MySQL** .

## 🔑 Key Features (Summary)

> This project is a simplified book rental system. Focus on implementing the specified features correctly and defend any design choices you make (e.g., rental permissions, DB schema).

* Authentication & Authorization (Basic Auth)
* Two roles: **USER** and **ADMIN**
* Two endpoint categories:

  * **Public** (e.g., registration, login) — accessible to anyone
  * **Private** (requires authentication; may also require specific authorization)
* Admin-only operations: creating, updating, deleting books (as specified)
* Business rules (e.g., rental limits) enforced by service layer
* Logging of application events and errors
* Unit tests using **MockMvc** + **Mockito** (minimum 10 tests)
* Generate a runnable **JAR** and include run instructions
* Public Postman collection included in README

## 📂 Postman Collection

### Option - 1

Access the Learning Navigator API collection using the link below.
**[Importer Link to Postman Collection](https://www.postman.com/navigation-participant-9941289/collections/request/nq1lu0w/register-user)**

### Option - 2

Postman collection file included in postman/RentRead.postman_collection.json

Import the collection to test all endpoints quickly

## ✅ Requirements (Detailed)

### Authentication & Users

* Support user registration and login using email and password.
* Passwords must be hashed using  **BCrypt** .
* User fields: `email`, `password`, `firstName`, `lastName`, `role`.
* Default role: **USER** (when not specified).
* Use **Basic Auth** for protected endpoints.

### Book Management

* Book fields: `title`, `author`, `genre`, `availabilityStatus`.
* `availabilityStatus` indicates whether the book is available for rent.
* Any authenticated user may browse available books.
* Only admins may create, update, or delete books.

### Roles & Authorization

* Role `USER`: general access to browse and rent books (subject to business rules).
* Role `ADMIN`: full management rights for books (create, update, delete).
* Private endpoints require authentication; some private endpoints also require role-based authorization.

### Rental Management

* Users can rent available books.
* A user may have  **at most two active rentals** . If the limit is reached, the service must return an error.
* Users can return books they have rented.
* Rental updates must correctly change book availability.

### Validation & Error Handling

* Validate inputs and handle common error scenarios.
* Return appropriate HTTP status codes (e.g., `400`, `403`, `404`, `409`, `500`).
* Use a global exception handler (`@RestControllerAdvice`) to centralize error responses.

### Logging & Tests

* Log informational events and errors via a logging framework (e.g., SLF4J + Logback).
* Include at least 10 unit tests using **MockMvc** and  **Mockito** .

## 📦 Deliverables & Documentation

* Meaningful, incremental commit messages (use Conventional Commits where possible).
* A descriptive `README.md` that documents running, testing, and API usage.
* Include a public Postman collection link in the README.

## 🧩 Implementation Notes

* Use a layered architecture:  **Entity → Repository → Service → Controller** .
* Design database schema carefully; be ready to justify decisions (e.g., relationships, indexes).
* Enforce foreign-key constraints in MySQL where appropriate.

## 🌐 Base URL

``http://localhost:8081/``

## 🔗 API Endpoints (User Documentation)

> Example request/response payloads are included for clarity.

### 1. Create Admin Account

**POST** `/auth/signup`
*Request:*

```json
{
  "email": "admin@rentread.com",
  "password": "admin123456",
  "firstName": "admin",
  "lastName": "test",
  "role": "ADMIN"
}
```

*Response:* (`201 Created`):

```json
{
  "id": 6,
  "firstName": "admin",
  "lastName": "test",
  "email": "admin@rentread.com",
  "role": "ADMIN"
}
```

### 2. Create User Account

**POST** /auth/signup
*Request:*

```json
{
  "email": "user.test@example.com",
  "password": "user123456",
  "firstName": "RegularTest",
  "lastName": "UserTest"
}
```

*Response:* (`201 Created`):

```json
{
  "id": 7,
  "firstName": "RegularTest",
  "lastName": "UserTest",
  "email": "user.test@example.com",
  "role": "USER"
}
```

### 3. Login (User/Admin)

**POST** /auth/login
*Request:*

```json
{
  "email": "admin@rentread.com",
  "password": "admin123456"
}
```

*Response:* (`200 OK`):

```json
{
  "id": 6,
  "firstName": "admin",
  "lastName": "test",
  "email": "admin@rentread.com",
  "role": "ADMIN"
}
```

### 4. Create Book (Admin only)

**POST** /books
*Request:*

```json
{
  "title": "Test Book 1",
  "author": "Test Author 1",
  "genre": "FICTION",
  "availabilityStatus": "AVAILABLE"
}
```

*Response:* (`201 Created`):

```json
{
  "id": 17,
  "title": "Test Book 1",
  "author": "Test Author 1",
  "genre": "FICTION",
  "availabilityStatus": "AVAILABLE"
}
```

### 5. Delete Book (Admin only)

**DELETE** /books/{book_id}
*Request:*

```json
{} 
```

*Response:* (`204 No Content`):

```json
{}
```

### 6. Update Book (Admin only)

**PUT** /books/{book_id}

*Request:*

```json
{
  "id": 17,
  "title": "Test Book 1",
  "author": "Test Author 123",
  "genre": "FICTION",
  "availabilityStatus": "AVAILABLE"
}
```

*Response:* (`200 OK`):

```json
{
  "id": 17,
  "title": "Test Book 1",
  "author": "Test Author 123",
  "genre": "FICTION",
  "availabilityStatus": "AVAILABLE"
}
```

### 7. Forbidden for Regular Users (Example)

**PUT** /books/{book_id}
*Response:* (`403 Forbidden`):

```json
{
  "message": "Access Denied: You don't have permission to perform this action",
  "httpStatus": "FORBIDDEN",
  "localDateTime": "2025-11-29T09:41:53.624575"
}
```

### 8. Get all Available Books

**GET** /books/available
*Response:* (`200 Ok`):

```json
[
    {
        "id": 4,
        "title": "Test Book 2",
        "author": "Test Author 2",
        "genre": "FICTION",
        "availabilityStatus": "AVAILABLE"
    },
    {
        "id": 5,
        "title": "Test Book Two",
        "author": "Test Author Two",
        "genre": "FICTION",
        "availabilityStatus": "AVAILABLE"
    }
]
```

### 9. Rent a Book

**POST** /rentals/users/{userId}/books/{bookId}
`Request:`

```json
{}
```

*Response:* (`201 Created`):

```json
{
  "id": 5,
  "book": {
    "id": 17,
    "title": "Test Book 1",
    "author": "Test Author 123",
    "genre": "FICTION",
    "availabilityStatus": "NOT_AVAILABLE"
  },
  "rentedAt": "2025-03-10",
  "returnDate": null
}
```

### 10. Get Active Rentals for a User

**GET** /rentals/active-rentals/users/{userId}
*Response:* (`200 OK`):

```json
[
  {
    "id": 5,
    "book": {
      "id": 17,
      "title": "Test Book 1",
      "author": "Test Author 123",
      "genre": "FICTION",
      "availabilityStatus": "NOT_AVAILABLE"
    },
    "rentedAt": "2025-03-10",
    "returnDate": null
  }
]
```

### 11. Return a Book

**PUT** /rentals/{rental_id}
*Request:*

```json
{}
```

*Response:* (`204 No Content`)

```json
{}
```

### 11. Enforce Rental Limit (Example Error)

`If a user already has two active rentals and attempts another:`

**POST** /rentals/users/{userId}/books/{bookId}
`Request:`

```json
{}
```

*Response:* (`400 Bad Request`):

```json
{
  "message": "User '1' exceed maximum rental count",
  "httpStatus": "BAD_REQUEST",
  "localDateTime": "2025-11-29T09:41:53.624575"
}
```
