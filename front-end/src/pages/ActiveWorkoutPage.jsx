import { useState, useEffect } from "react"
// import { useParams, useNavigate } from "react-router-dom";
// import Box from '@mui/material/Box';
// import "../css/ActiveWorkoutPage.css";
// import { Typography} from "@mui/material";
// import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../utils/HelperFunctions";


export default function ActiveWorkoutPage({activeWorkout, activeUsername}) {
    const [allWorkouts, setAllWorkouts] = useState([])
    const [userWorkouts, setUserWorkouts] = useState([])

    console.log('active username is', activeUsername)

    const getAllWorkouts = async () => {
        const response = await getAxiosCall("http://localhost:8080/api/workouts")
        if (response) {
            console.log('response for all workouts is', response)
            setAllWorkouts(response)
        }
        else {
            console.log("no response for get all workouts")
        }
    }

    const getUserWorkouts = async () => {
        const response = await getAxiosCall(`http://localhost:8080/api/workouts/user/${activeUsername}`)
        if (response) {
            console.log('response for get User workouts', response)
            setUserWorkouts(response)
        }
        else {
            console.log("no response for get user workouts")
        }
    }

    useEffect(()=> {
        getAllWorkouts();
        getUserWorkouts();
      }, [])

    return (
        <>
        <h2>WorkoutPage</h2>
        <h3>Testing ALL workouts</h3>
        {allWorkouts[0] ? (
            allWorkouts.map((workout, index) => {
            console.log('in workout map');
            return <p key={index}>{workout.title}</p>;
    })
) : (
    <p>There are no historical workouts to display</p>
)}

<h3>Testing USER workouts</h3>
        {userWorkouts[0] ? (
            userWorkouts.map((workout, index) => {
            console.log('in user workout map');
            return <p key={index}>{workout.title}</p>;
    })
) : (
    <p>There are no USER workouts to display</p>
)}
        <h3>Current Exercises in Active Workout:</h3>
        {activeWorkout[0]? (
            activeWorkout.map((exercise)=> {
                return <p>{exercise.name}</p>
            })
        )    : (
        <p>Add an exercise to see your workout!</p>
        )}
        </>
    )
}   
    // todos:
// need a way to save the workout - either completed or plan for the future