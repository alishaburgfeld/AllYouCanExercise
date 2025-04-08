// import { useState, useEffect } from "react"
// import { useParams, useNavigate } from "react-router-dom";
import "../css/ActiveWorkoutPage.css";
import { Box, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../utils/HelperFunctions";
import { useState } from 'react';

import SetsRepsDuration from "../components/Workout/SetsRepsDuration";


// need to send set order and exercise order 
export default function ActiveWorkoutPage({activeWorkout, activeUsername, workoutDetails, setWorkoutDetails}) {
    const theme = useTheme();
    console.log('active username is', activeUsername)
    console.log('workoutdetails in activeworkout page are', workoutDetails)
    console.log('activeWorkout in activeworkout page are', activeWorkout)

    // useEffect(()=> {
    //   }, [])

    const handleExerciseClick= (exerciseId) => {
        navigate(`/exercise/${exerciseId}`);
    }

    return (
        <Box className="activeWorkout">
            <Typography className="activeWorkout_title" sx={{fontSize:"1.8rem", pt:"4rem", color: theme.palette.secondary.main}}>
                Active Workout
            </Typography>
            <Box className="activeWorkout_ItemContainer">
            {activeWorkout[0]
                ? (
                activeWorkout.map((exercise)=> (
                <Box key={exercise.id} className="activeWorkout_items" sx={{pb:5, borderRadius: 1, border:2, borderColor: theme.palette.secondary.main, position: "relative"}}>
                    <Box onClick={() => handleExerciseClick(exercise.id)} >
                        <img src={getImageSource(exercise.name)} className="activeWorkout_exercisePhoto" alt={exercise.name}/>
                        <Typography align="center" className="activeWorkout_exerciseName"> {exercise.name}</Typography>
                    </Box>
                    <SetsRepsDuration className="activeWorkout_setsRepsDuration" exercise={exercise} workoutDetails={workoutDetails} />
                </Box>
                )))
                : (
                <p>Add an exercise to see your workout!</p>
                )}
            </Box> 
        </Box>
    )
}   