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

const getInitialUsername = () => {
  const activeUsername = sessionStorage.getItem("activeUsername");
  return activeUsername ? JSON.parse(activeUsername) : null
}

function App() {

  const [activeWorkout, setActiveWorkout] = useState([]);
  const [workoutDetails, setWorkoutDetails] = useState([])
  const [activeUsername, setActiveUsername] =useState(getInitialUsername());

  console.log('active username on app,jsx', activeUsername)

  const setExerciseInfo =(exercise) =>{
    let exerciseInfo = {}
    if (exercise.type==="CARDIO") {
      exerciseInfo["exerciseId"] = exercise.id
      exerciseInfo["duration"] = 15
    }
    else {
      exerciseInfo= {"sets": 4, "reps": 10, "weight": 10}
    }
    return exerciseInfo;
  }

  const addToActiveWorkout= (exercise) => {
    setActiveWorkout((prevActiveWorkout) => [...prevActiveWorkout, exercise]);
    setWorkoutDetails((prevWorkoutDetails) => [...prevWorkoutDetails, setExerciseInfo()]);
  };


  useEffect(() => {
    sessionStorage.setItem("activeUsername", JSON.stringify(activeUsername))
  }, [activeUsername])


// I don't think I need my check for users function anymore. Leaving this comment here in case I run into issues with getting the active username once I have more tha one user.
//  check commit 7a7fdd8 for the checkUser function.

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
            <Route path="/workout" element={<ActiveWorkoutPage activeWorkout={activeWorkout} activeUsername={activeUsername}/>} />
            <Route path="/workout/:workoutId" element={<ViewWorkoutPage />} />
          {/* the variable name after : must match the variable name you set with <variable> = useParams() */}
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
