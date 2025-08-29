import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { toTitleCase, convertJavaLocalDateTimeToUserLocalTime } from "../../utils/HelperFunctions"


export default function WorkoutHistoryCard({workout}) {
    const navigate = useNavigate();
  const [userWorkouts, setUserWorkouts] = useState(null);
  const [workoutTitle, setWorkoutTitle] = useState(null);
  const [workoutCompletedDate, setWorkoutCompletedDate] = useState(null);
    const[workoutExerciseGroups, setWorkoutExerciseGroups] = useState(null);

const handleWorkoutClick = () => {
    navigate(`/workout/${workout.workoutId}`);
  }

  const setWorkoutInformation = () => {
    const workoutDetails = workout.workoutDetails;
    const workoutExerciseDetails = workout.workoutExerciseDetails;
    setWorkoutTitle(workoutDetails.title);
    // console.log('typeOf', typeof(workoutDetails.completedAt))
    const formattedCompletedDate = convertJavaLocalDateTimeToUserLocalTime(workoutDetails.completedAt)
    setWorkoutCompletedDate(formattedCompletedDate);
    console.log('workoutexerciseDetails', workoutExerciseDetails)
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
    console.log('exercisegroups are', exerciseGroups)

    setWorkoutExerciseGroups(exerciseGroups.join());
  }

  

  useEffect(()=> {
    setWorkoutInformation();
    
  }, [workout])

    return (
    <Box sx={{ minWidth: 8/10, maxWidth:9/10, mt:"1rem", maxHeight: 3/10, display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center', }}>
        
      <Card variant="outlined" sx={{borderRadius:7, display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',}}>
        <>
        
        <CardContent>
        <Typography variant="h5" component="div">
            {workoutTitle}
        </Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>Completed: {workoutCompletedDate}</Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>Targeted: </Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>{workoutExerciseGroups}</Typography>
        </CardContent>
        <CardActions>
            <Button 
            size="small"
            onClick={handleWorkoutClick}
            >
              <Typography variant="h6">View Details</Typography>    
            </Button>
        </CardActions>
        </>
      </Card>
    </Box>
  );
}