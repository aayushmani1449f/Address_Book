import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { Moon, Sun, BookUser } from 'lucide-react';
import { ThemeContext } from '../context/ThemeContext';

const Navbar = () => {
  const { isDarkMode, toggleTheme } = useContext(ThemeContext);

  return (
    <nav className="navbar">
      <Link to="/" className="nav-brand">
        <BookUser size={28} />
        <span>AddressBook</span>
      </Link>
      
      <div className="nav-links">
        <Link to="/" className="nav-link">Home</Link>
        <Link to="/add" className="nav-link">Add Contact</Link>
        <button onClick={toggleTheme} className="btn-icon" aria-label="Toggle Theme">
          {isDarkMode ? <Sun size={24} /> : <Moon size={24} />}
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
