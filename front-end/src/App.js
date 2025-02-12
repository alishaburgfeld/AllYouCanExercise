import './App.css';
import { useEffect, useState, useRef } from 'react';
// use {thing to be imported} when there are a lot of things to be imported from that file.
//when using the "export default" this allows you n ot to use the {} on the thing you're importing
// import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import axios from 'axios'
import Cookies from 'js-cookie';
import Homepage from './pages/Homepage';
import Navbar from './components/Navbar/Navbar'
import ExercisePage from './pages/ExercisePage'
import ExerciseGroupPage from './pages/ExerciseGroupPage';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import theme from './utils/theme'

function App() {

  return (
    <div className="App">
      <ThemeProvider theme={theme}>
      <CssBaseline />
          <BrowserRouter> 
          <Navbar />
          <Routes>
            <Route path='/' element={<Homepage />} />
            <Route path='/exercises/:exerciseGroup' element = {<ExerciseGroupPage />} />
            <Route path='/:exerciseId' element = {<ExercisePage />} />
          </Routes>
        </BrowserRouter> 
      </ThemeProvider>
    </div>
  );
}

export default App;
