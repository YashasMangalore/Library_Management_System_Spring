# Library Management API

This project is a RESTful API for managing a library system. It provides endpoints for managing transactions, teachers, students, cards, books, and authors. Additionally, it integrates with a third-party news API to provide book-related news.

## Table of Contents

- [Getting Started](#getting-started)
- [Endpoints](#endpoints)
  - [Transaction Controller](#transaction-controller)
  - [Teacher Controller](#teacher-controller)
  - [Student Controller](#student-controller)
  - [Card Controller](#card-controller)
  - [Book Controller](#book-controller)
  - [Author Controller](#author-controller)
- [Schemas](#schemas)
- [License](#license)

## Getting Started

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/library-management-api.git
    ```

2. Navigate to the project directory:
    ```sh
    cd library-management-api
    ```

3. Build the project with Maven:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

The API server should now be running at `http://localhost:8080`.

## Endpoints

### Transaction Controller

- **PUT /api/v1/transactions/return**
  - Process a return transaction.

- **PUT /api/v1/transactions/reminder**
  - Send a reminder for a transaction.

- **PUT /api/v1/transactions/issue**
  - Issue a transaction.

### Teacher Controller

- **PUT /api/v1/teacher/update**
  - Update teacher information.
  - Request Body: `UpdateTeacherRequest`

- **POST /api/v1/teacher/add**
  - Add a new teacher.
  - Request Body: `Teacher`

- **GET /api/v1/teacher/all-teachers**
  - Retrieve all teachers.

- **GET /api/v1/teacher/all-students**
  - Retrieve all students associated with teachers.

- **GET /api/v1/teacher/all-students-details**
  - Retrieve detailed information of all students.

- **DELETE /api/v1/teacher/remove**
  - Remove a teacher.

### Student Controller

- **PUT /api/v1/student/update**
  - Update student information.
  - Request Body: `UpdateStudentRequest`

- **PUT /api/v1/student/associate-student-teacher**
  - Associate a student with a teacher.

- **POST /api/v1/student/add**
  - Add a new student.
  - Request Body: `Student`

- **GET /api/v1/student/teacher**
  - Retrieve the teacher associated with a student.

- **GET /api/v1/student/all-students**
  - Retrieve all students.

- **DELETE /api/v1/student/remove**
  - Remove a student.

### Card Controller

- **PUT /api/v1/card/associate-card-student**
  - Associate a card with a student.

- **POST /api/v1/card/add**
  - Add a new card.

- **DELETE /api/v1/card/remove**
  - Remove a card.

### Book Controller

- **PUT /api/v1/book/update**
  - Update book information.
  - Request Body: `UpdateBookRequest`

- **PUT /api/v1/book/associate-book-author**
  - Associate a book with an author.

- **POST /api/v1/book/add**
  - Add a new book.
  - Request Body: `Book`

- **GET /api/v1/book/news**
  - Retrieve news about books.

- **GET /api/v1/book/find-author-title**
  - Find a book by author and title.

- **DELETE /api/v1/book/remove**
  - Remove a book.

### Author Controller

- **PUT /api/v1/author/update**
  - Update author information.
  - Request Body: `UpdateAuthorRequest`

- **POST /api/v1/author/add**
  - Add a new author.
  - Request Body: `Author`

- **GET /api/v1/author/find-author-name**
  - Find an author by name.

- **GET /api/v1/author/find-author-id**
  - Find an author by ID.

- **GET /api/v1/author/books**
  - Retrieve books by a specific author.

- **GET /api/v1/author/books-details**
  - Retrieve detailed information of books by an author.

- **DELETE /api/v1/author/remove**
  - Remove an author.

## Schemas

- `UpdateTeacherRequest`: Schema for updating teacher information.
- `UpdateStudentRequest`: Schema for updating student information.
- `UpdateBookRequest`: Schema for updating book information.
- `UpdateAuthorRequest`: Schema for updating author information.
- `Teacher`: Schema for teacher information.
- `Student`: Schema for student information.
- `Author`: Schema for author information.
- `Book`: Schema for book information.
- `TeacherResponse`: Schema for teacher response data.
- `ArticleResponse`: Schema for article response data.
- `NewsResponse`: Schema for news response data.
- `AuthorResponse`: Schema for author response data.
- `BookResponse`: Schema for book response data.
