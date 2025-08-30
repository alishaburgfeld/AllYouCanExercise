import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { useTheme } from '@mui/material/styles';
import { toTitleCase, convertJavaLocalDateTimeToUserLocalTime } from "../../utils/HelperFunctions"
import WorkoutAccordion from './WorkoutAccordion';


export default function WorkoutHistoryCard({workout}) {
  const theme = useTheme();
    const navigate = useNavigate();
  const [workoutTitle, setWorkoutTitle] = useState(null);
  const [workoutCompletedDate, setWorkoutCompletedDate] = useState(null);
  const[workoutExerciseGroups, setWorkoutExerciseGroups] = useState(null);
  const [workoutNotes, setWorkoutNotes] = useState(null);
  const [workoutExerciseDetails, setWorkoutExerciseDetails] = useState(null);
  const [joinedExerciseGroups, setJoinedExerciseGroups] = useState(null);

  console.log('workoutEx group on history card', workoutExerciseGroups)
  const handleWorkoutClick = () => {
    navigate(`/workout/${workout.workoutId}`);
  }

  const setWorkoutInformation = () => {
    const workoutDetails = workout.workoutDetails;
    const workoutExerciseDetails = workout.workoutExerciseDetails;
    setWorkoutExerciseDetails(workoutExerciseDetails);
    setWorkoutTitle(workoutDetails.title);
    setWorkoutNotes(workoutDetails.workoutNotes);
    // console.log('typeOf', typeof(workoutDetails.completedAt))
    const formattedCompletedDate = convertJavaLocalDateTimeToUserLocalTime(workoutDetails.completedAt)
    setWorkoutCompletedDate(formattedCompletedDate);
    console.log('workoutexerciseDetails on history card', workoutExerciseDetails)
    determineExerciseGroups(workoutExerciseDetails);
  }

  const determineExerciseGroups = (workoutExerciseDetails) => {
    const exerciseGroups = [];
    workoutExerciseDetails.forEach((exercise)=> {
        console.log('exercise is', exercise)
        const eg = exercise.exerciseGroup
        const egTitleCase = toTitleCase(eg);
        if(!exerciseGroups.includes(egTitleCase)) {
          exerciseGroups.push(egTitleCase);
        }
    })
    console.log('exercisegroups are on history card', exerciseGroups)

    setWorkoutExerciseGroups(exerciseGroups);
    setJoinedExerciseGroups(exerciseGroups.join());
  }

  

  useEffect(()=> {
    setWorkoutInformation();
    
  }, [workout])

    return (
      <>
      {workoutExerciseGroups !== null ?

        <Box sx={{ minWidth: 8/10, maxWidth:9/10, mt:"1rem", maxHeight: 3/10, display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center', 
            }}>
            
          <Card variant="outlined" className= "workoutHistoryCard" sx={{borderRadius:7, display: 'flex',
            flexDirection: 'column', borderColor: theme.palette.secondary.main,
            borderWidth: 2,
            borderStyle: 'solid', 
            alignItems: 'center',
            justifyContent: 'center',
            mb:0,
            backgroundColor: "transparent"
            }}>
            <>
            
            <CardContent>
            <Typography variant="h5" component="div" sx={{mb: 1.5}}>
                {workoutTitle}
            </Typography>
            <Box sx={{ display: 'flex', flexWrap: 'wrap', alignItems: 'center' }}>
              <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Completed: </Typography>
              <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{workoutCompletedDate}</Typography>
            </Box>
            
            <Box sx={{ display: 'flex', flexWrap: 'wrap', alignItems: 'center', mb: 1 }}>
              <Typography sx={{ color: 'text.primary', mb: 1.5, mr:"5px" }}>Targeted: </Typography>
              <Typography sx={{ color: 'text.primary', mb: 1.5 }}>{joinedExerciseGroups}</Typography>
            </Box>
            {/* <Divider sx={{ mb: 0 }} /> */}
            </CardContent>
            {/* <CardActions sx={{ pt: 0 }}> */}
                {/* <Button 
                size="small"
                onClick={handleWorkoutClick}
                sx={{ textTransform: 'none' }}
                >
                  <Typography variant="h5" component="div" sx={{ color: theme.palette.primary.dark}}>View Details</Typography>    
                </Button> */}
                <WorkoutAccordion workoutNotes={workoutNotes} workoutExerciseDetails={workoutExerciseDetails} workoutExerciseGroups={workoutExerciseGroups}/>
            {/* </CardActions> */}
            </>
          </Card>
        </Box>
      
      : ""
      }
      
      </>
  );
}