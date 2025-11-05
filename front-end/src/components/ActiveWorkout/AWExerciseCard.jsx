import { Box, Card, CardMedia, CardContent, Button, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import { getImageSource, getAxiosCall } from "../../utils/HelperFunctions";
import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import SetsRepsDuration from "./SetsRepsDuration";

function AWExerciseCard ({exercise, setActiveWorkout, activeWorkout, updateActiveWorkoutWithNewStats}) {

    const theme = useTheme();
    const navigate = useNavigate();

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
        <Card 
            key={exercise.id}
            sx=
            {{
                borderRadius: "8px",
                transition: "box-shadow 0.3s ease",
                border:2, 
                cursor: "pointer",
                borderColor: theme.palette.secondary.main,
                "&:hover": {
                    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
                    "& img": {
                        transform: "scale(1.05)",
                    },
                },
            }}
        >
            <Box sx={{position: "relative", width: "100%", borderBottom: "1px solid",
                borderColor: "divider",}}>
                <CardMedia src={getImageSource(exercise.name)} 
                    onClick={() => handleExerciseClick(exercise.exerciseId)} 
                    className="active_exercise_photo" 
                    alt={exercise.name}
                    component="img"
                    sx={{
                        width: "100%",
                        height: { xs: 180, sm: 220, md: 260, lg: 300 },
                        objectFit: "cover",
                        transition: "transform 0.3s ease",
                        "&:hover": { transform: "scale(1.05)" },
                    }}
                    
                />
                <IconButton
                    className="delete-button"
                    onClick={() => handleRemoveExercise(exercise.exerciseId)}
                    sx={{
                        position: "absolute",
                        bottom: 5,
                        right: 5,
                    }}
                >
                    <DeleteIcon />
                </IconButton>
            </Box>

            <CardContent
                sx={{
                    backgroundColor: "#fafafa"
                }}>
                <Box>
                    <Box onClick={() => handleExerciseClick(exercise.exerciseId)}>

                    <Typography sx={{fontSize:theme.fontSize.primary}} className="activeWorkout_exerciseName">{exercise.name}</Typography>
                    </Box>
                    
                    <SetsRepsDuration className="activeWorkout_setsRepsDuration" exercise={exercise} activeWorkout={activeWorkout} updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}/>

                </Box>
            </CardContent>
        </Card>
    )
}

export default AWExerciseCard