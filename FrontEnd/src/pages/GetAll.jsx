import React, { useEffect, useState } from 'react';
import "../assets/view.css";

export default function GetAll() {
    const [employees, setEmployees] = useState([]);  // Stores all employees
    const [filteredEmployees, setFilteredEmployees] = useState([]); // Stores filtered results
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        fetch("http://localhost:9090/api/employee/getAll")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch data from the server");
                }
                return response.json();
            })
            .then((data) => {
                setEmployees(data);
                setFilteredEmployees(data); // Initially, show all employees
                setLoading(false);
            })
            .catch((error) => {
                console.error("Error fetching employees:", error);
                setLoading(false);
            });
    }, []);

    const handleSearch = () => {
        if (searchQuery.trim() === "") {
            setFilteredEmployees(employees); // Reset to show all employees if search is empty
            return;
        }

        const query = searchQuery.toLowerCase();

        // Filter employees based on name match
        const filtered = employees.filter((employee) =>
            employee.name.toLowerCase().includes(query)
        );

        setFilteredEmployees(filtered);
    };

    return (
        <div className='home-container'>
            <header className="header">
                <div className="header-content">
                    <h1>Employee List</h1>
                </div>
            </header>

            <main className="main-content">
                <section className="welcome-section">
                    <div className='search-container'>
                        <input 
                            type='text'
                            placeholder='Search by name'    
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            className='search-input'
                        />
                        <button onClick={handleSearch} className='search-button'>
                            Search
                        </button>
                    </div>

                    {loading ? (
                        <p>Loading.....</p>
                    ) : filteredEmployees.length > 0 ? (
                        <table border={1}>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Salary</th>
                                    <th>Phone Number</th>
                                    <th>Email</th>
                                    <th>Address</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredEmployees.map((employee) => (
                                    <tr key={employee.id}>
                                        <td>{employee.id}</td>
                                        <td>{employee.name}</td>
                                        <td>{employee.salary}</td>
                                        <td>{employee.phone}</td>
                                        <td>{employee.email}</td>
                                        <td>{employee.address}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No results found</p>
                    )}
                </section>
            </main>
        </div>
    );
}
