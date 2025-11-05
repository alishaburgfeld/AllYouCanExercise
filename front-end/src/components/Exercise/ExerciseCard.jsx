import Box from '@mui/material/Box';
import { Card, CardMedia, Typography, CardContent, IconButton,} from "@mui/material"
import { useState, useEffect} from "react"
import AddIcon from "@mui/icons-material/Add"
import {useNavigate } from "react-router-dom";
import { useTheme } from '@mui/material/styles';
import { getImageSource } from "../../utils/HelperFunctions"

function ExerciseCard ({activeWorkout, exercise, setExerciseToBeAdded, activeUsername, setActiveWorkout}) {

    const isAdded = activeWorkout && activeWorkout.some(e => e.exerciseId === exercise.id);

    
    const theme = useTheme();
    const navigate = useNavigate();

    const handleClick= (exerciseId) => {
        navigate(`/exercise/${exerciseId}`);
    }

    

    const handleRemoveExercise = (exerciseId) => {
      // Remove exercise by filtering out the one with matching id
      let updatedActiveWorkout = activeWorkout.filter(exercise => exercise.exerciseId !== exerciseId);
      if (updatedActiveWorkout.length===0) {
        updatedActiveWorkout=null;
      }
      setActiveWorkout(updatedActiveWorkout);
    };

    const addOrRemoveToWorkout = (exercise) => {
        if (!activeWorkout?.some(e => e.exerciseId === exercise.id)) {
            setExerciseToBeAdded(exercise);
        } else {
            handleRemoveExercise(exercise.id);
        }
    };


    return (
        <Card 
            key={exercise.id} className="exerciseGroup_items" 
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
                    "& .add-button": {
                        opacity: 1,
                    },
                },

            }}
        >
            <Box sx={{position: "relative", width: "100%", borderBottom: "1px solid",
                borderColor: "divider",}}>

            
                <CardMedia src={getImageSource(exercise.name)} 
                    onClick={() => handleClick(exercise.id)} 
                    className="exerciseGroup_photo" 
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
                {activeUsername !== null ? (
                    <>
                <IconButton
                    className="add-button"
                    onClick={() => addOrRemoveToWorkout(exercise)} 
                    sx={{
                        position: "absolute",
                        bottom: 8,
                        right: 8,
                        // backgroundColor: "rgba(255, 255, 255, 0.9)",
                        opacity: .85,
                        transition: "opacity 0.3s ease",
                        "&:hover": {
                            backgroundColor: "rgba(255, 255, 255, 1)",
                        },
                        // "&:active": {
                        //     backgroundColor: theme.palette.secondary.dark,
                        // },
                        color: isAdded ? theme.palette.primary.dark
                            : theme.palette.secondary.dark,
                        backgroundColor: 
                        isAdded ? theme.palette.secondary.main // stays orange after click ✅
                            : theme.palette.primary.light,
                    }}
                    >
                    <AddIcon />
                    </IconButton>
                </>
                ): ""
                }
            </Box>
                <CardContent
                onClick={() => handleClick(exercise.id)} 
                sx={{
                    backgroundColor: "#fafafa"
                }}>
                <Typography 
                    align="center" 
                    className="exerciseGroup_name"> {exercise.name}</Typography>

            </CardContent>
        </Card>
    )
}

export default ExerciseCard