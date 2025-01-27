import './App.css';
import { useEffect, useState, useRef } from 'react';
// use {thing to be imported} when there are a lot of things to be imported from that file.
//when using the "export default" this allows you n ot to use the {} on the thing you're importing
// import './App.css'
import { HashRouter as Router, Routes, Route } from 'react-router-dom';
import axios from 'axios'
import Homepage from './pages/Homepage';
import Navbar from './components/Navbar/Navbar'
import ExercisePage from './pages/ExercisePage'

function App() {

  
  return (
    <div className="App">
        <Router> 
        <Navbar />
        <Routes>
          <Route path='/' element={<Homepage />} />
          {/* <Route path='/login' element={<LoginPage/>} /> */}
          {/* <Route path='/signup' element = {<SignUpPage />} /> */}
          {/* <Route path='/game' element = {<GamePage user={user} whoAmI={whoAmI} hand={hand} setHand={setHand} game={game} setGame = {setGame}/>} /> */}
          {/* <Route path='/draft' element = {<Draft/>} /> */}
          <Route path="/exercise/:id" element={<ExercisePage/>} />
        </Routes>
      </Router> 
    </div>
  );
}

export default App;
