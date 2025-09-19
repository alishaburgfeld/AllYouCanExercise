import { useState, useEffect } from "react"
import { getAxiosCall } from "../utils/HelperFunctions";
import { useParams, useNavigate } from "react-router-dom";
import "../css/UserWorkoutsPage.css";
import { Box, Button, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import WorkoutHistoryCard from "../components/CompletedWorkouts/WorkoutHistoryCard";

export default function UserWorkoutsPage({activeUsername}) {
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const theme = useTheme();
    const [userWorkouts, setUserWorkouts] = useState(null);

const getUserWorkouts = async () => {
    const response = await getAxiosCall(`${VITE_API_BASE_URL}/workouts/user/${activeUsername}`)
    if (response) {
        // console.log('response for get User workouts', response)
        const sortedWorkouts = sortWorkouts(response);
        setUserWorkouts(sortedWorkouts)
        // console.log(
        //     'response in getUserWorkouts is', response
        // )
    }
    else {
        // console.log("no response for get user workouts")
    }
}

const sortWorkouts =(workouts)=> {
  const sortedWorkouts = [...workouts].sort((a, b) => {
      const dateA = new Date(a.workoutDetails?.completedAt);
      const dateB = new Date(b.workoutDetails?.completedAt);
      return dateB - dateA; // descending: most recent first
    });
    // console.log('sortedWorkouts are', sortedWorkouts)
    return sortedWorkouts;
}

useEffect(()=> {
    getUserWorkouts();
  }, [])

  return (
      <Box className="userWorkouts">
        <Typography className="userWorkouts_title" sx={{ fontSize: "1.8rem", pt: "4rem", color: theme.palette.secondary.main }}>
          Workout History
        </Typography>
        <Box className="userWorkouts_ItemContainer"
        sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        // minHeight: '100vh',
        // padding: 1,
      }}>
          {userWorkouts?.length > 0 ? (
            userWorkouts.map((workout, index) => (
                <WorkoutHistoryCard workout = {workout}/>
            ))
          ) : (
            <p>Complete a workout to see your history!</p>
          )}
        </Box>
      </Box>
    );
}