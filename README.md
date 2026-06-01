# 📚 RentRead — Book Rental Management System

RentRead is a RESTful backend application built using Spring Boot for managing an online book rental system.
The application provides secure authentication and authorization, book management, rental tracking, validation, centralized exception handling, database migration support using Flyway, and containerized deployment using Docker.

---

# 🚀 Features

## 🔐 Authentication & Authorization

* HTTP Basic Authentication using Spring Security
* Role-based access control (RBAC)
* Two roles supported:

  * `ADMIN`
  * `USER`
* BCrypt password hashing
* Public and protected endpoints
* Method-level authorization for admin-only operations

---

## 📚 Book Management

* Create, update, delete books (ADMIN only)
* Browse available books
* Track book availability status

---

## 🔄 Rental Management

* Rent available books
* Return rented books
* Restrict users to maximum 2 active rentals
* Automatically update book availability during rent/return operations

---

## ✅ Validation & Error Handling

* Request validation using Jakarta Validation
* Global exception handling using `@RestControllerAdvice`
* Meaningful HTTP status codes
* Consistent API error response structure

---

## 🛢 Database Management

* MySQL database integration
* Flyway database migration support
* Schema versioning and validation
* Foreign key constraints and relational integrity

---

## 🐳 Docker Support

* Dockerized Spring Boot application
* Docker Compose support for application + MySQL
* Easy local setup using containers

---

## 🧪 Testing

* Unit tests using:

  * MockMvc
  * Mockito
  * JUnit 5
* Security and controller layer testing
* Minimum 10 unit tests implemented

---

# 🏗 Tech Stack

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java 17         | Programming Language           |
| Spring Boot     | Backend Framework              |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | ORM                            |
| MySQL           | Relational Database            |
| Flyway          | Database Migration             |
| Docker          | Containerization               |
| Docker Compose  | Multi-container orchestration  |
| Gradle          | Build Tool                     |
| JUnit 5         | Testing                        |
| Mockito         | Mocking                        |
| MockMvc         | API Testing                    |
| Lombok          | Boilerplate Reduction          |

---

# 🏛 Architecture

The project follows a layered architecture:

```text
Controller → Service → Repository → Database
```

## Layers

### Controller Layer

Handles HTTP requests and responses.

### Service Layer

Contains business logic and validation rules.

### Repository Layer

Handles database interaction using Spring Data JPA.

### Entity Layer

Represents database tables and relationships.

---

# 🔐 Security Design

The application uses Spring Security with HTTP Basic Authentication.

## Public Endpoints

Accessible without authentication:

* `/auth/signup`
* `/auth/login`

## Protected Endpoints

Require authentication.

## Authorization Rules

| Endpoint            | Access      |
| ------------------- | ----------- |
| GET available books | USER, ADMIN |
| Create book         | ADMIN       |
| Update book         | ADMIN       |
| Delete book         | ADMIN       |
| Rent books          | USER, ADMIN |
| Return books        | USER, ADMIN |

---

# 🛢 Database Design

## Main Tables

* users
* books
* rentals

## Relationships

* One user can have many rentals
* One book can have many rentals historically
* One active rental per book at a time

## Important Constraints

* Unique email for users
* Foreign key constraints
* Rental limit validation
* Book availability validation

---

# ✈️ Flyway Migration

Flyway is used for:

* schema versioning
* migration tracking
* database consistency
* automatic schema validation

Migration scripts are located inside:

```text
src/main/resources/db/migration
```

---

# 📂 Postman Collection

## Option - 1

Access the Learning Navigator API collection using the link below.
**[Importer Link to Postman Collection](https://www.postman.com/navigation-participant-9941289/collections/request/nq1lu0w/register-user)**

## Option - 2

Postman collection file included in postman/RentRead.postman_collection.json

Import the collection to test all endpoints quickly

---

# 🐳 Docker Setup

## Build Images and Start Containers

```bash id="6n57rj"
docker compose up --build
```

This command:

* Builds the application image
* Starts MySQL container
* Starts RentRead container

---

## Start Existing Containers in Detached Mode

```bash id="zj70om"
docker compose up -d
```

Runs containers in the background.

---

## Stop Containers

```bash id="9lm1c0"
docker compose down
```


# ⚙️ Environment Variables

Example configuration:

```yaml
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/rent_read
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password
SPRING_PROFILES_ACTIVE=local
```

---

# ▶️ Running Locally

## 1. Clone Repository

```bash
git clone <repository-url>
cd rent-read
```

---

## 2. Build Application

```bash
./gradlew clean build
```

---

## 3. Run Application

```bash
java -jar build/libs/rent-read-0.0.1-SNAPSHOT.jar
```

Application runs on:

```text
http://localhost:8081
```

---

# 🧪 Running Tests

```bash
./gradlew test
```

---

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

# 🔮 Future Improvements
- JWT Authentication
- Refresh Tokens
- API Rate Limiting
- Pagination & Sorting
- API Documentation using Swagger/OpenAPI
- Distributed Caching using Redis
- CI/CD Pipeline
- Kubernetes Deployment
- Notification System
- Audit Logging

# 👨‍💻 Author

Mohammad Azkar