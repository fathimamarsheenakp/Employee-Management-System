import React from "react";
import "./assets/style.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import GetAll from "./pages/GetAll";

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/getAll" element={<GetAll/>}/>
\      </Routes>
    </Router>
  )
}

export default App
