import './App.css';
import { useEffect, useState, useRef } from 'react';
// use {thing to be imported} when there are a lot of things to be imported from that file.
//when using the "export default" this allows you n ot to use the {} on the thing you're importing
// import './App.css'
import { HashRouter as Router, Routes, Route } from 'react-router-dom';
import axios from 'axios'
import Cookies from 'js-cookie';
import Homepage from './pages/Homepage';
import Navbar from './components/Navbar/Navbar'
import ExercisePage from './pages/ExercisePage'
import ExerciseGroupPage from './pages/ExerciseGroupPage';

function App() {

  const [exercises, setExercises] = useState([]);

  const getExercises = async () => {
    const csrfToken = Cookies.get('XSRF-TOKEN'); // Read the CSRF token from the cookie
  
    try {
      const response = await axios.get('http://localhost:8080/api/exercises', {
        headers: {
          'X-XSRF-TOKEN': csrfToken,
        },
        withCredentials: true, // Include cookies in the request
      });
      console.log("response is", response, "response.data is", response.data);
      setExercises(response.data);
    } catch (error) {
      console.error("Error fetching exercises:", error);
    }
  };

  useEffect(()=> {
    getExercises();
  }, [])

  return (
    <div className="App">
        <Router> 
        <Navbar />
        <Routes>
          <Route path='/' element={<Homepage exercises={exercises}/>} />
          {/* <Route path='/login' element={<LoginPage/>} /> */}
          {/* <Route path='/signup' element = {<SignUpPage />} /> */}
          <Route path='/exercise/group/:exercise_group' element = {<ExerciseGroupPage exercises={exercises}/>} />
          {/* <Route path='/draft' element = {<Draft/>} /> */}
          <Route path='/exercise/:id' element={<ExercisePage exercises={exercises}/>} />
        </Routes>
      </Router> 
    </div>
  );
}

export default App;
