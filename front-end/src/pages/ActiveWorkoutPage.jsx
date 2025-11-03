// import { useState, useEffect } from "react"
// import { useParams, useNavigate } from "react-router-dom";
import "../css/ActiveWorkoutPage.css";
import { Box, Button, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../utils/HelperFunctions";
import { useState } from 'react';
import DeleteIcon from '@mui/icons-material/Delete';
import CompleteWorkoutModal from "../components/ActiveWorkout/CompleteWorkoutModal";
import SetsRepsDuration from "../components/ActiveWorkout/SetsRepsDuration";
import Alert from '@mui/material/Alert';
import { useNavigate } from "react-router-dom";

export default function ActiveWorkoutPage({ activeWorkout, setActiveWorkout, activeUsername, updateActiveWorkoutWithNewStats}) {
    const theme = useTheme();
    const navigate = useNavigate();
    const [openCompleteWorkoutModal, setOpenCompleteWorkoutModal] = useState(false);
    const [isWorkoutSaved, setIsWorkoutSaved] = useState(false);
    const [saveWorkoutError, setSaveWorkoutError] = useState(null);
    
    // TO-DO: Change theme fontSize based on if there is 1 or more exercises in activeworkout
  
    const handleExerciseClick = (exerciseId) => {
      navigate(`/exercise/${exerciseId}`);
    };
  
    const handleRemoveExercise = (exerciseId) => {
      // Remove exercise by filtering out the one with matching id
      let updatedActiveWorkout = activeWorkout.filter(exercise => exercise.exerciseId !== exerciseId);
      if (updatedActiveWorkout.length===0) {
        updatedActiveWorkout=null;
      }
      setActiveWorkout(updatedActiveWorkout);
    };
  
    return (
      <Box className="activeWorkout">
        <Typography className="activeWorkout_title" sx={{ fontSize: "1.8rem", pt: "4rem", color: theme.palette.secondary.main }}>
          Active Workout
        </Typography>
        <Box className="activeWorkout_ItemContainer">
          {activeWorkout?.length > 0 ? (
            activeWorkout.map((exercise, index) => (
              <Box key={index} className="activeWorkout_items" sx={{ pb: 5, borderRadius: 1, border: 2, borderColor: theme.palette.secondary.main, position: "relative" }}>
                <DeleteIcon onClick={() => handleRemoveExercise(exercise.exerciseId)} />
                <Box onClick={() => handleExerciseClick(exercise.exerciseId)} sx={{mb:"1rem"}}>
                  <img src={getImageSource(exercise.name)} className="activeWorkout_exercisePhoto" alt={exercise.name} />
                  <Typography sx={{fontSize:theme.fontSize.primary}} className="activeWorkout_exerciseName">{exercise.name}</Typography>
                </Box>
                <SetsRepsDuration className="activeWorkout_setsRepsDuration" exercise={exercise} activeWorkout={activeWorkout} updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}/>
              </Box>
            ))
          ) : (
            <p>Add an exercise to see your workout!</p>
          )}
        </Box>
        {activeWorkout?.length > 0 ? (
        <Button
          type="submit"
          variant="contained"
          color="primary"
          sx={{ marginBottom: 2 }}
          onClick={()=> {setOpenCompleteWorkoutModal(true)}}
        >
          Complete Workout
        </Button>
        ): "" }
        {isWorkoutSaved ?
                <Alert severity="success">You have saved your workout!</Alert>
                : ""}
                {saveWorkoutError!==null ?
                <Alert severity="error">{saveWorkoutError}!</Alert>
                : ""}
        <CompleteWorkoutModal openCompleteWorkoutModal={openCompleteWorkoutModal} setOpenCompleteWorkoutModal={setOpenCompleteWorkoutModal} activeWorkout={activeWorkout} setActiveWorkout={setActiveWorkout} saveWorkoutError={saveWorkoutError} setSaveWorkoutError={setSaveWorkoutError} isWorkoutSaved={isWorkoutSaved} setIsWorkoutSaved={setIsWorkoutSaved} activeUsername={activeUsername}/>
      </Box>
    );
}