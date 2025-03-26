import React, { useState } from 'react'
import "../assets/search.css";

export default function Search() {

  const [id, setId] = useState('');
  const [employee, setEmployee] = useState(null);
  const [error, setError] = useState(''); // Error message from the server

  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent page reload on form submit

    if (!id.trim() || isNaN(id)) {
      setError('Please enter a valid ID.');
      setEmployee(null);
      return;
    }

    // Fetch employee details from the server
    fetch(`http://localhost:9090/api/employee/get/${id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Employee not found');
        }
        return response.json();
      })
      .then((data) => {
        setEmployee(data); // Set employee details
        setError(''); // Clear any previous error
        setId(''); // Clear the input field
      })
      .catch((error) => {
        console.error('Error fetching employee:', error);
        setEmployee(null);
        setError('Employee not found');
      })
  }

  return (
    <div className='home-container'>
      <header className="header">
        <div className="header-content">
          <h1>Search Employee</h1>
        </div>
      </header>
      <main className="main-content">
        <section className='welcome-section'>
          <form onSubmit={handleSubmit}>
            <div className='search-container'>
                <input 
                  value={id}
                  onChange={(e) => setId(e.target.value)}
                  type="number" 
                  placeholder="Search by id" 
                  required
                  className='search-input'
                />
                <button type='submit' className='search-button'>
                  Search
                </button>
            </div>
          </form>

          {error && <p className='error-message'>{error}</p>}

          {/* Display employee details */}
          {employee && (
            <div className='employee-details'>
              <h2>EMPLOYEE DETAILS</h2>
              <p><strong>ID:</strong> {employee.id}</p>
              <p><strong>Name:</strong> {employee.name}</p>
              <p><strong>Salary:</strong> {employee.salary}</p>
              <p><strong>Phone:</strong> {employee.phone}</p>
              <p><strong>Email:</strong> {employee.email}</p>
              <p><strong>Address:</strong> {employee.address}</p>
            </div>
          )}

        </section>
      </main>

      {/* Footer */}
      <footer className="footer">
                <p>&copy; {new Date().getFullYear()} EmployeePro. All rights reserved.</p>
        </footer>
    </div>
  )
}
