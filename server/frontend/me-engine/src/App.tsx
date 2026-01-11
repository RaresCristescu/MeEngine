import './App.css'
import React from 'react';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import Message from './pages/Message';
import Navbar from './components/Navbar';
import 'bootstrap-icons/font/bootstrap-icons.css';

const Layout: React.FC = () => {
  const location = useLocation();

  // Hide navbar on login & register
  const hideNavbar = location.pathname === "/" || location.pathname === "/register";

  return (
    <>
      {!hideNavbar && <Navbar />}

      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/messages" element={<Message />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </>
  );
};

const App: React.FC = () => {
  return (
    <Router>
      <Layout />
    </Router>
  );
};

export default App;
