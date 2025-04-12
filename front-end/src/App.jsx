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

const getInitialActiveWorkout = () => {
  const activeWorkout = sessionStorage.getItem("activeWorkout");
  return activeWorkout ? JSON.parse(activeWorkout) : null
}

const getInitialWorkoutDetails = () => {
  const workoutDetails = sessionStorage.getItem("workoutDetails");
  return workoutDetails ? JSON.parse(workoutDetails) : null
}

function App() {

  const [activeWorkout, setActiveWorkout] = useState(getInitialActiveWorkout());
  const [exerciseToBeAdded, setExerciseToBeAdded] = useState(null);
  const [workoutDetails, setWorkoutDetails] = useState(getInitialWorkoutDetails());
  const [activeUsername, setActiveUsername] =useState(getInitialUsername());

  console.log('active username, active workout, workoutDetails on app,jsx', activeUsername, activeWorkout, workoutDetails)
  // explains why strict mode causes this console log to render twice: https://chatgpt.com/share/67f3d8fb-12f8-800f-9475-560f78c153f4

  const setExerciseInfo =(exercise) =>{
    if (exercise) {

      let exerciseInfo = {}
      if (exercise.exerciseType==="CARDIO") {
        exerciseInfo["exerciseId"] = exercise.id
        exerciseInfo["duration"] = 930
      }
      else {
        exerciseInfo= {"sets": 4, "reps": 10, "weight": 10}
        exerciseInfo["exerciseId"] = exercise.id
      }
      console.log('exerciseInfo is', exerciseInfo)
      return exerciseInfo;
    }
  }

  const addToActiveWorkout= (exerciseToBeAdded) => {
    console.log('in addtoActiveworkout')
    let updatedActiveWorkout;
    let updatedWorkoutDetails
    if (activeWorkout && workoutDetails && !activeWorkout.includes(exerciseToBeAdded) && !workoutDetails.includes(exerciseToBeAdded)) {
      updatedActiveWorkout = [...activeWorkout, exerciseToBeAdded];
      updatedWorkoutDetails = [...workoutDetails, setExerciseInfo(exerciseToBeAdded)];
    }
    else {
      updatedActiveWorkout = [exerciseToBeAdded];
      updatedWorkoutDetails = [setExerciseInfo(exerciseToBeAdded)];
    }
    console.log('updatedAtiveWorkout is', updatedActiveWorkout, "updatedWorkoutDetails is", updatedWorkoutDetails)
  //   setActiveWorkout((prevActiveWorkout) => [...prevActiveWorkout, exercise]);
  //   console.log('prior to adding a new one, activeworkout is', activeWorkout)
  //   console.log('prior to adding a new one, workoutdetails is', workoutDetails)
  //   setWorkoutDetails((prevWorkoutDetails) => [...prevWorkoutDetails, setExerciseInfo(exercise)]);
  setActiveWorkout(updatedActiveWorkout);
  setWorkoutDetails(updatedWorkoutDetails);
};

const checkForUserSession= async () => {
  // had to add checkForUser back in. Without it I was running into a problem where my backend hadn't yet created a session/csrf token, so I had to click the login button twice.

    const response = await getAxiosCall('http://localhost:8080/auth/checkusersession');
    if (response) {
      setActiveUsername(response);
    }
    else {
      console.log("no username returned in checkforuser session")
    }
  }

useEffect(()=> {
  checkForUserSession();
}, [])

  useEffect(() => {
    sessionStorage.setItem("activeUsername", JSON.stringify(activeUsername))
  }, [activeUsername])

  useEffect(() => {
    sessionStorage.setItem("activeWorkout", JSON.stringify(activeWorkout))
  }, [activeWorkout])

  useEffect(() => {
    sessionStorage.setItem("workoutDetails", JSON.stringify(workoutDetails))
  }, [workoutDetails])

  useEffect(() => {
    // only run this function if exerciseToBeAdded has a value, not on first render when its null
    if (exerciseToBeAdded) {
      addToActiveWorkout(exerciseToBeAdded);
    }
  }, [exerciseToBeAdded]);


  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <BrowserRouter>
          <Navbar activeUsername={activeUsername} setActiveUsername={setActiveUsername} setActiveWorkout={setActiveWorkout} setWorkoutDetails={setWorkoutDetails}/>
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="/login" element={<LoginPage setActiveUsername={setActiveUsername}/>} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route
              path="/exercises/:exerciseGroup"
              element={<ExerciseGroupPage />}
            />
            <Route path="/exercise/:exerciseId" element={<ExercisePage setExerciseToBeAdded= {setExerciseToBeAdded} activeUsername={activeUsername}/>} />
            <Route path="/workout" element={<ActiveWorkoutPage activeWorkout={activeWorkout} activeUsername={activeUsername} workoutDetails={workoutDetails} setWorkoutDetails={setWorkoutDetails}/>} />
            <Route path="/workout/:workoutId" element={<ViewWorkoutPage />} /> 
          {/* the variable name after : must match the variable name you set with <variable> = useParams() */}
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
