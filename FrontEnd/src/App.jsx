import React from "react";
import "./assets/style.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import GetAll from "./pages/GetAll";
import Add from "./pages/Add";
import Search from "./pages/Search";
import Update from "./pages/Update";
import Edit from "./pages/Edit";
import Delete from "./pages/Delete";

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/view" element={<GetAll/>}/>
        <Route path="/add" element={<Add/>}/>
        <Route path="/search" element={<Search/>}/>
        <Route path="/update" element={<Update/>}/>
        <Route path="/edit/:id" element={<Edit />} />
        <Route path="/delete" element={<Delete />} />
\      </Routes>
    </Router>
  )
}

export default App
