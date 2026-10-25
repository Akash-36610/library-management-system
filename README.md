# Library Management System

## Overview

A console-based Library Management System developed using Java, JDBC, and MySQL.

This application allows users to manage books, students, and issue/return records efficiently.

---

## Features

### Book Management

- Add Books
- View All Books
- Search Book By ID
- Delete Book
- Increase Book Quantity
- Decrease Book Quantity
- Set Overall Quantity

### Student Management

- Add Student
- View All Students
- Search Student By Registration Number
- Delete Student
- Update Student Phone Number
- Update Student Mail ID
- Update Student Year

### Issue & Return Management

- Issue Books
- Return Books
- View All Records
- View Issued Records
- View Returned Records
- View Overdue Records
- View Late Returned Books
- View Records By Student
- View Records By Book

---

## Technologies Used

- Java
- JDBC
- MySQL

---

## Concepts Used

- Object Oriented Programming (OOP)
- DAO Pattern
- Exception Handling
- Transactions
- PreparedStatement
- Enums
- Input Validation
- Configuration Properties

---

## Project Structure

```text
source
├── DAO
├── model
├── util
└── main
```

## Database

Database Name:

```text
library_management
```

---

## Configuration

Create a file named:

```text
config.properties
```

Example:

```properties
db.url=jdbc:mysql://localhost:3306/library_management
db.user=root
db.password=your_password
```

---

## Author

Akash