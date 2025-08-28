import { useState, useEffect } from "react"
import { getAxiosCall } from "../utils/HelperFunctions";
import { useParams, useNavigate } from "react-router-dom";

export default function ViewWorkoutPage({activeUsername}) {
    const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    const [workoutById, setWorkoutById] = useState({})
    const { workoutId } = useParams();
    const [userWorkouts, setUserWorkouts] = useState([])
    // console.log('workoutId is', workoutId)

const getWorkoutById = async () => {
    const response = await getAxiosCall(`${VITE_API_BASE_URL}/workouts/${workoutId}`)
    if (response) {
        // console.log('response for workout by id is', response)
        console.log('🎯 FINAL Axios response:', response);
        setWorkoutById(response)
    }
    else {
        // console.log("no response for get workout by id")
    }
}


const getWorkoutTest = async () => {
    const response = await getAxiosCall(`${VITE_API_BASE_URL}/workouts/test`)
    if (response) {
        // console.log('response for get User workouts', response)
        // setUserWorkouts(response)
        console.log(
            'response in getWorkoutTest is', response
        )
    }
    else {
        // console.log("no response for get user workouts")
    }
}

useEffect(()=> {
    getWorkoutById();
    
  }, [workoutId])

  return (
    <>
    <h3>Showing Workout with id 2</h3>
        {workoutById !== null ? (
            <p >{workoutById.title}</p>)
        : (
            <p>There is no workout to display</p>
        )}
    </>
  )
}