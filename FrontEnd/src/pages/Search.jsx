import React, { useState } from 'react'

export default function Search() {

  const [id, setId] = useState('');

  return (
    <div className='home-container'>
      <header className="header">
        <div className="header-content">
          <h1>Employee List</h1>
        </div>
      </header>
      <main className="main-content">
        <section className='welcome-section'>
          <form>
            <input 
              value={id}
              onChange={(e) => setId(e.target.value)}
              type="text" 
              placeholder="Search by id" 
            />
            <button>Search</button>
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
