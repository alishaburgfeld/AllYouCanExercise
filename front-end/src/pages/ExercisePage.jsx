
import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import { getAxiosCall } from "../utils/HelperFunctions"
import { getImageSource } from "../utils/HelperFunctions";
import PlaylistAddIcon from '@mui/icons-material/PlaylistAdd';
import { Typography } from "@mui/material";
import Box from '@mui/material/Box';
import { useTheme } from '@mui/material/styles';
import { IconButton } from "@mui/material";
import "../css/ExercisePage.css"


function ExercisePage({addToActiveWorkout}) {
    const theme = useTheme();

    const { exerciseId } = useParams();
    const [exercise, setExercise] = useState({});
    const [exerciseHistory, setExerciseHistory] = useState([])
    const [exerciseRecord, setExerciseRecord] = useState([])
    
    // add call to get history and records
    const getExercise = async () => {
        const response = await getAxiosCall(`http://localhost:8080/api/exercises/${exerciseId}`);
        if (response) {
            setExercise(response);
        }
        else {
            console.error("Error fetching exercise");
        }
    };

  useEffect(()=> {
    getExercise();
  }, [exerciseId])

    return (
        <Box className="exercisePage">
            <Typography className="exercisePage_title" sx={{fontSize:"1.8rem", pt:"4rem", mt: "3.2rem", mb: "1rem", color: theme.palette.secondary.main}}>
                {exercise.name}
            </Typography>
            <IconButton className = "exercisePage_addToWorkout" aria-label="add-to-workout" sx={{color: theme.palette.secondary.main, position:"absolute", top:"8%", right:"45%"}} onClick={() => addToActiveWorkout(exercise)}>
                    <PlaylistAddIcon fontSize ="large"/>
                </IconButton>
            <Box className="exercisePage_ItemContainer" sx={{ padding: '1rem' }}>
                <img src={getImageSource(exercise.name)} className="exercisePage_image" alt={exercise.name}/>
                <Box className="exercisePage_description" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                    <Typography className = "exercisePage_description_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}> Description </Typography>
                    <Typography className = "exercisePage_description_text"> {exercise.description} </Typography>
                </Box>
                <Box className="exercisePage_history" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                    <Typography className = "exercisePage_history_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}>Workout History</Typography>
                    {exerciseHistory[1]?
                        <span>{exerciseHistory[1]}</span>
                    :
                        <span>Complete a workout to see your history!</span>
                    }
                </Box>
                <Box className="exercisePage_records" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                <Typography className = "exercisePage_records_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}>Records</Typography>
                    
                    {exerciseRecord[1]?
                        <span>{exerciseRecord["date or whatever"]}</span>
                    :
                        <span>Complete a workout to see your max reps/weight!</span>
                    }
                </Box>
                   
            </Box>
        </Box>
    );
}

export default ExercisePage;

// todos:
// add the ability to add a photo