import { useState, useEffect } from "react"
import { getAxiosCall } from "../utils/HelperFunctions";
import { useParams, useNavigate } from "react-router-dom";
// import "../css/UserWorkoutsPage.css";
import { Box, Button, Typography, IconButton} from "@mui/material";
import { useTheme } from '@mui/material/styles';
import WorkoutHistoryCard from "../components/CompletedWorkouts/WorkoutHistoryCard";
import Header from "../components/Shared/Header";

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
      <Box className="userWorkouts" sx={{ minHeight: "100vh", pt:12, justifyContent: "center", position: "relative"}} >
        <Header title={"Workout History"} typographyClassName={"userWorkouts_title"} />
        <Box className="userWorkouts_ItemContainer"
        sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        width: {xs: "90vw", m: "60vw", l: "50vw"},
        py: 3,
        mx: "auto"
      }}>
          {userWorkouts?.length > 0 ? (
            userWorkouts.map((workout, index) => (
                <WorkoutHistoryCard workout = {workout} key={index}/>
            ))
          ) : (
            <p>Complete a workout to see your history!</p>
          )}
        </Box>
      </Box>
    );
}