import React from "react";
import "./assets/style.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import GetAll from "./pages/GetAll";
import Add from "./pages/Add";
import Search from "./pages/Search";

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/view" element={<GetAll/>}/>
        <Route path="/add" element={<Add/>}/>
        <Route path="/search" element={<Search/>}/>
\      </Routes>
    </Router>
  )
}

export default App
