import { useState, useEffect } from "react"
import { getAxiosCall } from "../utils/HelperFunctions";
import { useParams, useNavigate } from "react-router-dom";

export default function ViewWorkoutPage() {
    const [workoutById, setWorkoutById] = useState({})
    const { workoutId } = useParams();
    console.log('workoutId is', workoutId)

const getWorkoutById = async () => {
    const response = await getAxiosCall(`http://localhost:8080/api/workouts/${workoutId}`)
    if (response) {
        console.log('response for workout by id is', response)
        setWorkoutById(response)
    }
    else {
        console.log("no response for get workout by id")
    }
}

useEffect(()=> {
    getWorkoutById();
  }, [workoutId])

  return (
    <>
    <h3>Showing Workout with id 1</h3>
        {workoutById !== null ? (
            <p >{workoutById.title}</p>)
        : (
            <p>There is no workout to display</p>
        )}
    </>
  )
}