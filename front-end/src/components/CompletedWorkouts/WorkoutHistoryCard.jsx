import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react"


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
    setWorkoutCompletedDate(workoutDetails.completedAt);
    console.log('workoutexerciseDetails', workoutExerciseDetails)
    const exerciseGroups = [];
    workoutExerciseDetails.forEach((exercise)=> {
        console.log('exercise is', exercise)
        exerciseGroups.push(exercise.exerciseGroup);
    })
    console.log('exercisegroups are', exerciseGroups)
    setWorkoutExerciseGroups(exerciseGroups);
  }

  useEffect(()=> {
    setWorkoutInformation();
    
  }, [workout])

    return (
    <Box sx={{ minWidth: 275 }}>
      <Card variant="outlined">
        <>
        
        <CardContent>
        <Typography variant="h5" component="div">
            {workoutTitle}
        </Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>Completed: </Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>{workoutCompletedDate}</Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>Targeted:</Typography>
        <Typography sx={{ color: 'text.secondary', mb: 1.5 }}>{workoutExerciseGroups}</Typography>
        </CardContent>
        <CardActions>
            <Button 
            size="small"
            onClick={handleWorkoutClick}
            >
                View Details</Button>
        </CardActions>
        </>
      </Card>
    </Box>
  );
}