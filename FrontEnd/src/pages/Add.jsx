import React, { useState } from 'react'
import "../assets/add.css";

export default function Add() {

    const [name, setName] = useState('');
    const [salary, setSalary] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');


    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent page reload on form submit

        // Validate input fields
        if (!name || !salary || !phone || !email || !address) {
            alert('Please fill in all fields.');
            return;
        }

        if (!name.trim()) {
            alert('Name is required and cannot be empty.');
            return;
        }
        if (!/^[a-zA-Z\s]+$/.test(name)) {
            alert('Name should only contain letters and spaces.');
            return;
        }
        if (!salary || isNaN(salary) || parseFloat(salary) <= 0) {
            alert('Salary must be a positive number.');
            return;
        }
        if (!phone.trim() || !/^\d{10}$/.test(phone)) {
            alert('Phone number must be a valid 10-digit number.');
            return;
        }
        if (!email.trim() || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            alert('Please enter a valid email address.');
            return;
        }

        if (!address.trim()) {
            alert('Address is required and cannot be empty.');
            return;
        }

        const employeeData = {
            name, 
            salary: parseFloat(salary), // Convert salary to number 
            phone, 
            email, 
            address
        };

        // Send employee data to backend
        fetch('http://localhost:9090/api/employee/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employeeData),
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to add employee");
            }
            return response.json();
        })
        .then((data) => {
            console.log('Employee added successfully:', data);
            alert('Employee added successfully!');
            // Optionally, reset the form fields
            setName('');
            setSalary('');
            setPhone('');
            setEmail('');
            setAddress('');
        })
        .catch((error) => {
        console.error("Error adding employee:", error);
        alert('Failed to add employee. Please try again.');
        })
    }

  return (
    <div className='home-container'>
        <header className="header">
            <div className="header-content">
                <h1>Employee List</h1>
            </div>
        </header>

        <main className="main-content">
            <section className='welcome-section'>
                <form className='add-form' onSubmit={handleSubmit}>
                    <div className='form-container'>
                        <div className='form-group'>
                            <label htmlFor='name'>Name:</label>
                            <input 
                                type='text' 
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                id='name' 
                                name='name' 
                                required 
                            />
                        </div>

                        <div className='form-group'>
                            <label htmlFor='salary'>Salary:</label>
                            <input 
                                type='number' 
                                value={salary}
                                onChange={(e) => setSalary(e.target.value)}
                                id='salary' 
                                name='salary' 
                                required 
                            />
                        </div>

                        <div className='form-group'>
                            <label htmlFor='phone'>Phone Number:</label>
                            <input 
                                type='tel' 
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)}
                                id='phone' 
                                name='phone' 
                                required 
                            />
                        </div>

                        <div className='form-group'>
                            <label htmlFor='email'>Email:</label>
                            <input 
                                type='email' 
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                id='email' 
                                name='email' 
                                required 
                            />
                        </div>

                        <div className='form-group'>
                            <label htmlFor='address'>Address:</label>
                            <textarea 
                                id='address' 
                                value={address}
                                onChange={(e) => setAddress(e.target.value)}
                                name='address' 
                                required>
                            </textarea>
                        </div>
                    </div>

                    <div className='form-group'>
                        <button type='submit' className='submit-button'>
                            Submit
                        </button>
                    </div>

                </form>
            </section>
        </main>

        {/* Footer */}
        <footer className="footer">
                <p>&copy; {new Date().getFullYear()} EmployeePro. All rights reserved.</p>
        </footer>
    </div>
  )
}
