// import { useState, useEffect } from "react"
// import { useParams, useNavigate } from "react-router-dom";
// import "../css/ActiveWorkoutPage.css";
import { Box, Button, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../utils/HelperFunctions";
import { useState } from 'react';
import DeleteIcon from '@mui/icons-material/Delete';
import CompleteWorkoutModal from "../components/ActiveWorkout/CompleteWorkoutModal";

import Alert from '@mui/material/Alert';
import { useNavigate } from "react-router-dom";
import AWExerciseCard from "../components/ActiveWorkout/AWExerciseCard";

export default function ActiveWorkoutPage({ activeWorkout, setActiveWorkout, activeUsername, updateActiveWorkoutWithNewStats}) {
    const theme = useTheme();
    const navigate = useNavigate();
    const [openCompleteWorkoutModal, setOpenCompleteWorkoutModal] = useState(false);
    const [isWorkoutSaved, setIsWorkoutSaved] = useState(false);
    const [saveWorkoutError, setSaveWorkoutError] = useState(null);
    
    // TO-DO: Change theme fontSize based on if there is 1 or more exercises in activeworkout
  
    
    return (
      <Box className="activeWorkout" sx={{ minHeight: "100vh", pt:12}}>
        <Box
          sx={{
          position: "fixed",
          top: 55,
          left: 0,
          right: 0,
          bgcolor: "background.default",
          // bgcolor: "red",
          borderBottom: "1px solid",
          borderColor: "divider",
          zIndex: 1000,
          py: 1,
          pt: 1.5,
          px: { xs: 2, sm: 3, lg: 4 },
          }}
        >   
          <Box sx={{
            position: "relative",
            maxWidth: "1280px",
            mx: "auto",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            pt: 4,
            }}
          >
            <Typography className="activeWorkout_title" sx={{ fontSize: "1.8rem", color: theme.palette.secondary.main }}>
              Active Workout
            </Typography>
          </Box>
        <Box className="activeWorkout_ItemContainer"
            sx={{ px: { xs: 2, sm: 3, lg: 4 }, py: 4, pt: 6,
                display: "grid",
                gap: 2,
                gridTemplateColumns: {
                xs: "repeat(1, 1fr)", // 2 columns by default
                md: "repeat(2, 1fr)", // 3 columns on medium+
                lg: "repeat(4, 1fr)", // 4 columns on large+
                },
            }}
            >
          {activeWorkout?.length > 0 ? (
            activeWorkout.map((exercise, index) => (
              <AWExerciseCard activeWorkout={activeWorkout} setActiveWorkout={setActiveWorkout} exercise={exercise} updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}/>
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
    </Box>
    );
}