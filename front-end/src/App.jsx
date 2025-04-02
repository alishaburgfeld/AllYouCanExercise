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
import ExerciseGroupPage from "../../front-end/src/pages/ExerciseGroupPage";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import theme from "../../front-end/src/utils/theme";
import axios from 'axios'
import Cookies from 'js-cookie';

function App() {

  const [workout, setWorkout] = useState([]);
  const [user, setUser] =useState(null)

  const addToWorkout= (exercise) => {
    setWorkout((prevWorkout) => [...prevWorkout, exercise]);
  };

  // when a user signs in it will say "successfully signed in, you may login now" and redirect to login page
  // wehn a users logs in it will say "successfully logged in", and will redirect to homepage.
  // this redirect should go back to app.jsx which should set the user.
  const getUser = async () => {
      try {
          const csrfToken = Cookies.get('XSRF-TOKEN');
          const response = await axios.get('http://localhost:8080/auth/checkusersession', {
            headers: {
            'X-XSRF-TOKEN': csrfToken,
            },
            withCredentials: true, 
        });
          console.log(response.data);
          setUser(response.data);
      } catch (error) {
          console.log("No active session");
      }
  };

  useEffect(()=> {
    getUser();
  }, [])


  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <BrowserRouter>
          <Navbar user={user}/>
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="/login" element={<LoginPage setUser={setUser}/>} />
            <Route path="/signup" element={<SignUpPage setUser={setUser}/>} />
            <Route
              path="/exercises/:exerciseGroup"
              element={<ExerciseGroupPage />}
            />
            <Route path="/:exerciseId" element={<ExercisePage addToWorkout={addToWorkout}/>} />
            <Route path="/:workout" element={<ExercisePage workout={workout}/>} />
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </div>
  );
}

export default App;
