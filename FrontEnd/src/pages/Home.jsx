import React, { useRef, useState } from 'react';
import { FiArrowRight } from "react-icons/fi";
import { useNavigate } from 'react-router-dom';

export default function Home() {
    const [showActions, setShowActions] = useState(false);
    const actionButtonsRef = useRef(null);
    const navigate = useNavigate();

    const handleGetStarted = () => {
        setShowActions(true);
        setTimeout(() => {
            actionButtonsRef.current.scrollIntoView({ behavior: 'smooth' });
        }, 100);
    }

    const handleViewAllEmployees = () => {
        navigate('/view');
    }

    const handleAddNewEmployee = () => {
        navigate('/add');
    }

    return (
        <div className="home-container">
            {/* Header */}
            <header className="header">
                <div className="header-content">
                    <h1>EmployeePro</h1>
                    <p>Streamlining Employee Onboarding, Management & Growth</p>
                </div>
            </header>

            {/* Main Content */}
            <main className="main-content">
                <section className="welcome-section">
                    <h2>Welcome to EmployeePro</h2>
                    <p className="welcome-text">
                        Welcome to <strong>EmployeePro</strong> — your ultimate tool for seamless employee management. 
                        Effortlessly onboard new team members, view and update employee details, or manage exits — all from one intuitive platform.
                    </p>

                    <div className="cta-container">
                        <button className="cta-button" onClick={handleGetStarted}>
                            Get Started <FiArrowRight className="icon" size={18} />
                        </button>
                    </div>

                    {/* Action Buttons */}
                    {showActions && (
                        <div className="action-buttons" ref={actionButtonsRef}>
                            <button className="action-button" onClick={handleViewAllEmployees}>View All Employees</button>
                            <button className="action-button" onClick={handleAddNewEmployee}>Add New Employee </button>
                            <button className="action-button">Search Employee</button>
                            <button className="action-button">Update Employee Details</button>
                            <button className="action-button">Remove Employee</button>
                            <button className="action-button">Export Employee Data</button>
                        </div>
                    )}
                </section>
            </main>

            {/* Footer */}
            <footer className="footer">
                <p>&copy; {new Date().getFullYear()} EmployeePro. All rights reserved.</p>
            </footer>
        </div>
    );
}
