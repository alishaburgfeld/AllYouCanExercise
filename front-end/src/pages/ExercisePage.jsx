import { useState } from "react"

function ExercisePage() {

    const [exerciseName,setExerciseName] = useState("biceps");

    return ("hard coded name is ${exerciseName}");
}

export default ExercisePage;