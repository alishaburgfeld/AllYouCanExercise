import "./App.css";
// use {thing to be imported} when there are a lot of things to be imported from that file.
//when using the "export default" this allows you n ot to use the {} on the thing you're importing
// import './App.css'
import { BrowserRouter, Route, Routes } from "react-router";
import { useState, useEffect } from "react"
import Homepage from "../../front-end/src/pages/Homepage";
import LoginPage from "../../front-end/src/pages/LoginPage";
import SignUpPage from "../../front-end/src/pages/SignUpPage";
import Navbar from "../../front-end/src/components/Navbar/Navbar";
import ExercisePage from "../../front-end/src/pages/ExercisePage";
import ActiveWorkoutPage from "../../front-end/src/pages/ActiveWorkoutPage";
import ViewWorkoutPage from "../../front-end/src/pages/ViewWorkoutPage";
import ExerciseGroupPage from "../../front-end/src/pages/ExerciseGroupPage";
import { ThemeProvider } from "@mui/material/styles";
import { getAxiosCall } from "./utils/HelperFunctions"
import CssBaseline from "@mui/material/CssBaseline";
import theme from "../../front-end/src/utils/theme";
import axios from 'axios'
import Cookies from 'js-cookie';

function App() {

  const [activeWorkout, setActiveWorkout] = useState([]);
  const [activeUsername, setActiveUsername] =useState(null)

  const addToActiveWorkout= (exercise) => {
    setActiveWorkout((prevActiveWorkout) => [...prevActiveWorkout, exercise]);
  };

  const checkForUser= async () => {
    // console.log("in get user")
    if (activeUsername) {
      const response = await getAxiosCall('http://localhost:8080/auth/checkusersession');
      if (response) {
        setActiveUsername(response);
      }
      else {
        console.log("no username returned")
      }
    }
  };

  useEffect(()=> {
    checkForUser();
  }, [])


  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <BrowserRouter>
          <Navbar activeUsername={activeUsername} setActiveUsername={setActiveUsername}/>
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="/login" element={<LoginPage setActiveUsername={setActiveUsername}/>} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route
              path="/exercises/:exerciseGroup"
              element={<ExerciseGroupPage />}
            />
            <Route path="/exercise/:exerciseId" element={<ExercisePage addToActiveWorkout={addToActiveWorkout}/>} />
            <Route path="/workout" element={<ActiveWorkoutPage activeWorkout={activeWorkout}/>} />
            <Route path="/workout/:workoutId" element={<ViewWorkoutPage />} />
          {/* the variable name after : must match the variable name you set with <variable> = useParams() */}
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
