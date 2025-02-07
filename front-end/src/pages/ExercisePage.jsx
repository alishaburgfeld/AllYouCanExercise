import { useState } from "react"

function ExercisePage({exercises}) {

    const [exerciseName,setExerciseName] = useState("biceps");

    return (`hard coded name is ${exerciseName}`);
}

export default ExercisePage;