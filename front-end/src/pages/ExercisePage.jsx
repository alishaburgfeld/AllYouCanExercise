
import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom";
import { getAxiosCall, convertJavaLocalDateTimeToUserLocalTime } from "../utils/HelperFunctions"
import { getImageSource } from "../utils/HelperFunctions";
import PlaylistAddIcon from '@mui/icons-material/PlaylistAdd';
import { Typography } from "@mui/material";
import Box from '@mui/material/Box';
import { useTheme } from '@mui/material/styles';
import { IconButton } from "@mui/material";
import Alert from '@mui/material/Alert';
import ExerciseAddedAlert from "../components/Exercise/ExerciseAddedAlert";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ExerciseRecord from "../components/Exercise/ExerciseRecord";
import Header from "../components/Shared/Header"

function ExercisePage({ setExerciseToBeAdded, activeUsername }) {
    const theme = useTheme();
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const { exerciseId } = useParams();
    const [exercise, setExercise] = useState({});
    const [openExerciseAddedAlert, setOpenExerciseAddedAlert] = useState(false);
    const [exerciseHistory, setExerciseHistory] = useState([])
    const [exerciseRecord, setExerciseRecord] = useState(null)
    const [lastExercised, setLastExercised] = useState(null);
    const navigate = useNavigate();
    
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

    const handleBackClick= () => {
        navigate(`/exercises/${exercise.exerciseGroup}`);
    }

    const getExerciseRecord = async () => {
      const er = await getAxiosCall(`${VITE_API_BASE_URL}/exercise-records/${activeUsername}/${exerciseId}`);
      if (er) {
        // console.log('exerciseRecord on exercise page', er)
        setExerciseRecord(er)
        const lastExercisedJava = er.lastExercised;
        const formattedLastExercised = convertJavaLocalDateTimeToUserLocalTime(lastExercisedJava);
        setLastExercised(formattedLastExercised);
      }
    }
  
    useEffect(() => {
      getExercise();
      getExerciseRecord();
    }, [exerciseId]);
  
    return (
      <Box className="exercisePage" sx={{ minHeight: "100vh",
         pt:12, backgroundColor: theme.palette.background.default, justifyContent: "center", alignItems: "center",
          width: "100vw"
         }}>
        <Header 
          title={exercise.name} 
          icon={ <ArrowBackIosIcon sx={{
              color: theme.palette.secondary.main,
              fill: theme.palette.secondary.main,
              fontSize: "2rem",
              }}/>
              } 
          onIconClick={() => handleBackClick()}
          typographyClassName={"exercise_title"}
          rightContent={
            <PlaylistAddIcon
              fontSize="large"
              sx={{
                color: theme.palette.secondary.main
              }}
              className="exercisePage_addToWorkout"
              aria-label="add-to-workout"
              onClick={() => handleClickToAddWorkout(exercise)}
            />
          }
        />
        {activeUsername !== null ? (
          <>
            <ExerciseAddedAlert openExerciseAddedAlert={openExerciseAddedAlert} setOpenExerciseAddedAlert={setOpenExerciseAddedAlert} />
          </>
        ) : (
          <span>Login to add workout</span>
        )}
        <Box className="exercisePage_ItemContainer" sx={{ padding: '1rem' }}>
                <Box
                  component="img"
                  src={getImageSource(exercise.name)}
                  alt={exercise.name}
                  className="exercisePage_image"
                  sx={{
                    pt: 5,
                    width: {xs:"40vw", s: "30vw", m:"30vw"},
                    height: {xs:"40vh", s: "40vh", m:"30vh"},
                    objectFit: "cover",
                    borderRadius: "8px",
                    transition: "transform 0.3s ease",
                    "&:hover": { transform: "scale(1.05)" },
                  }}
                />

                <Box className="exercisePage_description" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                    <Typography className = "exercisePage_description_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}> Description </Typography>
                    <Typography className = "exercisePage_description_text"> {exercise.description} </Typography>
                </Box>
                <Box className="exercisePage_history" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main, display: 'flex', alignItems: 'center', flexDirection: "column" }}>
                    <Typography className = "exercisePage_history_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}>Last Completed:</Typography>
                    {activeUsername === null ? (
                      <>
                        <span>Login to add workout</span>
                      </>
                      ) : (
                      exerciseRecord !== null ? (
                        <Typography sx={{ color: 'text.primary', mb: 1.5 }}>
                          {lastExercised}
                        </Typography>
                      ) : (
                        <span>Complete a workout to see your history!</span>
                      )
                    )}
                </Box>
                <Box className="exercisePage_records" sx={{mt:"1rem", borderRadius: 1, border:2, borderColor: theme.palette.secondary.main }}>
                <Typography className = "exercisePage_records_title" sx={{fontSize:"1.2rem",fontWeight: "600", mt:".5rem", mb:".5rem"}}>Records</Typography>
                  {activeUsername === null ? (
                      <>
                        <span>Login to add workout</span>
                      </>
                      ) : (  
                    <>
                    {exerciseRecord!== null?
                        <ExerciseRecord exerciseRecord={exerciseRecord} />
                    :
                        <Typography>Complete a workout to see your records!</Typography>
                    }
                    </>    
                  )}
                </Box>
                   
            </Box>
      </Box>
    );
  }
  

export default ExercisePage;

// todos:
// add the ability to add a photo