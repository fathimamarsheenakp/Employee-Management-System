# Employee Management System

This project is a **Java-based Employee Management System** designed to demonstrate database connectivity and CRUD operations using **MySQL**. The application features a graphical user interface (GUI) built with **JSwing**.

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

- **Programming Language**: Java  
- **Database**: MySQL  
- **GUI Library**: JSwing  

## Prerequisites

Before running the project, ensure you have the following installed:
- **Java Development Kit (JDK)**  
- **MySQL Server**  
- **MySQL Connector/J (JDBC Driver)**  
- **IDE** (e.g., Eclipse, IntelliJ IDEA, or NetBeans)

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

  
2. **Configure Database Connection**
   - Update the database credentials in your Java code:
     ```bash
     String url = "jdbc:mysql://localhost:3306/EmployeeDB";
     String username = "your_username";
     String password = "your_password";

4. **Run the Application**
   - Open the project in your preferred IDE.
   - Compile and run the main Java class to start the application.

## Usage

- **Start the Application**: Launch the GUI window.  
- **Perform Operations**: Use the intuitive GUI to perform CRUD operations:
    * View all employees.
    * Add, update, or delete employee records.
    * Search for an employee by their ID.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.
 


