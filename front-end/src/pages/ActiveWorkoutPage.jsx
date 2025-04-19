// import { useState, useEffect } from "react"
// import { useParams, useNavigate } from "react-router-dom";
import "../css/ActiveWorkoutPage.css";
import { Box, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../utils/HelperFunctions";
import { useState } from 'react';
import DeleteIcon from '@mui/icons-material/Delete';

import SetsRepsDuration from "../components/Workout/SetsRepsDuration";
import { Delete } from "@mui/icons-material";


export default function ActiveWorkoutPage({ activeWorkout, setActiveWorkout, activeUsername}) {
    const theme = useTheme();
    console.log('activeWorkout in ActiveWorkoutPage:', activeWorkout);
  
    const handleExerciseClick = (exerciseId) => {
      navigate(`/exercise/${exerciseId}`);
    };
  
    const handleRemoveExercise = (exerciseId) => {
      // Remove exercise by filtering out the one with matching id
      const updatedActiveWorkout = activeWorkout.filter(exercise => exercise.exerciseId !== exerciseId);
      setActiveWorkout(updatedActiveWorkout);
    };
  
    return (
      <Box className="activeWorkout">
        <Typography className="activeWorkout_title" sx={{ fontSize: "1.8rem", pt: "4rem", color: theme.palette.secondary.main }}>
          Active Workout
        </Typography>
        <Box className="activeWorkout_ItemContainer">
          {activeWorkout.length > 0 ? (
            activeWorkout.map((exercise) => (
              <Box key={exercise.exerciseId} className="activeWorkout_items" sx={{ pb: 5, borderRadius: 1, border: 2, borderColor: theme.palette.secondary.main, position: "relative" }}>
                <DeleteIcon onClick={() => handleRemoveExercise(exercise.exerciseId)} />
                <Box onClick={() => handleExerciseClick(exercise.exerciseId)}>
                  <img src={getImageSource(exercise.name)} className="activeWorkout_exercisePhoto" alt={exercise.name} />
                  <Typography align="center" className="activeWorkout_exerciseName">{exercise.name}</Typography>
                </Box>
                <SetsRepsDuration className="activeWorkout_setsRepsDuration" exercise={exercise} activeWorkout={activeWorkout}/>
              </Box>
            ))
          ) : (
            <p>Add an exercise to see your workout!</p>
          )}
        </Box>
      </Box>
    );
}
  


// need to send:
// {
// "workoutDetails": {
// "username": "xusername",
// "title": "xtitle",
// "completedAt": "2025-04-13T14:00:00",
// "notes": "xnotes"
// },
// "workoutExerciseDetails": [
// {
// "exerciseId": 1,
// "sets": [
// { "reps": 10, "weight": 50.0, "duration": 0, "distance": 0 },
// { "reps": 8, "weight": 55.0, "duration": 0, "distance": 0 }
// ]
// },
// {
// "id": 2,
// "sets": [
// { "reps": 15, "weight": 0.0, "duration": 900, "distance": 2000 },
// { "reps": 10, "weight": 0.0, "duration": 600, "distance": 1500 }
// ]
// }
// ]
// }