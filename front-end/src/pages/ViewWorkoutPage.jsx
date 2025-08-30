import { useState, useEffect } from "react"
import { getAxiosCall } from "../utils/HelperFunctions";
import { useParams, useNavigate } from "react-router-dom";
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

export default function ViewWorkoutPage({activeUsername}) {
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const [workoutById, setWorkoutById] = useState(null)
    const { workoutId } = useParams();
    console.log('workoutId is', workoutId)

const getWorkoutById = async () => {
    const response = await getAxiosCall(`${VITE_API_BASE_URL}/workouts/${workoutId}`)
    if (response) {
        // console.log('response for workout by id is', response)
        console.log('🎯 FINAL Axios response:', response);
        setWorkoutById(response)
    }
    else {
        // console.log("no response for get workout by id")
    }
}

useEffect(()=> {
    getWorkoutById();
    
  }, [workoutId])

  return (
    <>
    <Box>
        <Typography variant= "h1"> Showing Workout
            </Typography></Box>
        {/* {workoutById !== null ? (
            <p >{workoutById.title}</p>)
        : (
            <p>There is no workout to display</p>
        )} */}
    </>
  )
}