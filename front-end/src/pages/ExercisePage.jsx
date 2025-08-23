
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
import Alert from '@mui/material/Alert';
import ExerciseAddedAlert from "../components/Exercise/ExerciseAddedAlert";


function ExercisePage({ setExerciseToBeAdded, activeUsername }) {
    const theme = useTheme();
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const { exerciseId } = useParams();
    const [exercise, setExercise] = useState({});
    const [openExerciseAddedAlert, setOpenExerciseAddedAlert] = useState(false);
    const [exerciseHistory, setExerciseHistory] = useState([])
    const [exerciseRecord, setExerciseRecord] = useState([])
    
    const getExercise = async () => {
      const response = await getAxiosCall(`${VITE_API_BASE_URL}/exercises/${exerciseId}`);
      if (response) {
        setExercise(response);
      } else {
        console.error("Error fetching exercise");
      }
    };

    const getExerciseHistory = () => {
      
    }
  
    const handleClickToAddWorkout = (exercise) => {
      setExerciseToBeAdded(exercise); // Add the entire exercise object
      setOpenExerciseAddedAlert(true);
    };
  
    useEffect(() => {
      getExercise();
    }, [exerciseId]);
  
    return (
      <Box className="exercisePage">
        <Typography className="exercisePage_title" sx={{ fontSize: "1.8rem", pt: "4rem", mt: "3.2rem", mb: "1rem", color: theme.palette.secondary.main }}>
          {exercise.name}
        </Typography>
        {activeUsername !== null ? (
          <>
            <IconButton
              className="exercisePage_addToWorkout"
              aria-label="add-to-workout"
              sx={{
                color: theme.palette.secondary.main,
                position: "absolute",
                top: "8%",
                right: "45%",
              }}
              onClick={() => handleClickToAddWorkout(exercise)}
            >
              <PlaylistAddIcon fontSize="large" />
            </IconButton>
            <ExerciseAddedAlert openExerciseAddedAlert={openExerciseAddedAlert} setOpenExerciseAddedAlert={setOpenExerciseAddedAlert} />
          </>
        ) : (
          <span>Login to add workout</span>
        )}
        <Box className="exercisePage_ItemContainer" sx={{ padding: '1rem' }}>
                <img src={getImageSource(exercise.name)} className="exercisePage_image" alt={exercise.name}/>
                <Box className="exercisePage_description" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                    <Typography className = "exercisePage_description_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}> Description </Typography>
                    <Typography className = "exercisePage_description_text"> {exercise.description} </Typography>
                </Box>
                <Box className="exercisePage_history" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                    <Typography className = "exercisePage_history_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}>Last Completed:</Typography>
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