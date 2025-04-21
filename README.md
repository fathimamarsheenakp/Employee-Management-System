# Employee Management System

This project is a full-stack Employee Management System designed to demonstrate CRUD operations with a modern tech stack. It uses Spring Boot for backend REST APIs and ReactJS for the frontend interface. MySQL is used for data persistence.
## Features

The system provides the following functionalities:
1. **Fetch All Employees**  
   Retrieve and display all employees from the database.
2. **Onboard New Employee**  
   Add a new employee's details to the database.
3. **Fetch Employee by ID**  
   Search and display employee details using their unique ID.
4. **Update Employee by ID**  
   Modify an employee's details using their unique ID.
5. **Delete Employee by ID**  
   Remove an employee's record from the database using their unique ID.

## Technologies Used

- **Frontend**: ReactJS  
- **Backend**: Spring Boot (Java)  
- **Database**: MySQL
- **API Communication**: RESTful APIs
- **ORM**: Spring Data JPA
- **Build Tools**: Maven, npm

## Prerequisites

Make sure the following are installed on your system:
- **Java Development Kit** (JDK)
- **Node.js and npm**
- **MySQL Server**
- **MySQL Connector/J** (JDBC Driver)
- **An IDE** (e.g., IntelliJ IDEA for backend, VS Code for frontend)

## Setup Instructions

1. **Clone the Repository**  
      ```bash
      git clone <repository-url>
      cd employee-management-system

2. **Set Up the Database**
   - Create a MySQL database and a table to store employee details.
   - Use the following schema as an example:
     ```sql
     CREATE DATABASE EmployeeDB;
     
     USE EmployeeDB;
     
     CREATE TABLE Employees (
         id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(50) NOT NULL,
         department VARCHAR(50),
         salary DECIMAL(10, 2)
     );

  
3. **Configure database credentials:**
   - Update the database credentials in application.properties:
     ```bash
     spring.datasource.url=jdbc:mysql://localhost:3306/EmployeeDB
     spring.datasource.username=your_username
     spring.datasource.password=your_password

4. **Run the backend**
     ```bash
     mvn spring-boot:run

5. **Start the React app**
     ```bash
     npm install
     npm start

## Usage

- Launch the React app at http://localhost:3000  
- Perform CRUD operations via the user-friendly interface:
    * View all employees.
    * Add, update, or delete employee records.
    * Search for an employee by their ID.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.
 


