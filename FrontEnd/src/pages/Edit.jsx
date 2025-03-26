import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

export default function Edit() {

    const {id} = useParams();
    const [employee, setEmployee] = useState(null);
    const [error, setError] = useState(''); // Error message from the server
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
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
            })
            .catch((error) => {
                console.error('Error fetching employee:', error);
                setEmployee(null);
                setError('Employee not found');
            });
    }, [id]);

    const handleUpdate = (e) => {
        e.preventDefault(); // Prevent page reload on form submit

        // Update employee details on the server
        fetch(`http://localhost:9090/api/employee/update`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employee),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to update employee');
                }
                return response.json();
            })
            .then((data) => {
                console.log('Employee updated successfully:', data);
                setSuccessMessage('Employee details updated successfully');
            })
            .catch((error) => {
                console.error('Error updating employee:', error);
                setSuccessMessage('Failed to update employee details');
            });
    }

    if (error) {
        return <p className='error-message'>{error}</p>
    }

    if (!employee) {
        return <p>Loading...</p>
    }

  return (
    <div className='home-container'>
      <header className="header">
        <div className="header-content">
          <h1>Edit Details</h1>
        </div>
      </header>
      <main className="main-content">
        <section className='welcome-section'>
          <form onSubmit={handleUpdate}>
            <div className='form-container'>
                <div className='form-group'>
                    <label htmlFor='name'>Name:</label>
                    <input
                        type='text'
                        id='name'
                        value={employee.name}
                        onChange={(e) => setEmployee({...employee, name: e.target.value})}
                        required
                    ></input>
                </div>

                <div className='form-group'>
                    <label htmlFor='salary'>Salary:</label>
                    <input
                        type='number'
                        id='salary'
                        value={employee.salary}
                        onChange={(e) => setEmployee({ ...employee, salary: e.target.value })}
                        required
                    />
                </div>        
                
                <div className='form-group'>
                    <label htmlFor='phone'>Phone:</label>
                    <input
                    type='text'
                    id='phone'
                    value={employee.phone}
                    onChange={(e) => setEmployee({ ...employee, phone: e.target.value })}
                    required
                    />
                </div>     

                <div className='form-group'>
                    <label htmlFor='email'>Email:</label>
                    <input
                    type='email'
                    id='email'
                    value={employee.email}
                    onChange={(e) => setEmployee({ ...employee, email: e.target.value })}
                    required
                    />
                </div>     

                <div className='form-group'>
                    <label htmlFor='address'>Address:</label>
                    <input
                    type='text'
                    id='address'
                    value={employee.address}
                    onChange={(e) => setEmployee({ ...employee, address: e.target.value })}
                    required
                    />
                </div>      

                <button type='submit' className='edit-button'>Update</button>
            </div>
          </form>

          {successMessage && <p className='success-message'>{successMessage}</p>}
          {error && <p className='error-message'>{error}</p>}

        </section>
      </main>

      {/* Footer */}
      <footer className="footer">
                <p>&copy; {new Date().getFullYear()} EmployeePro. All rights reserved.</p>
        </footer>
    </div>
  )
}
