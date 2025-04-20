import { Box, Typography, IconButton} from "@mui/material";
import { Edit } from '@mui/icons-material';
import { formatExerciseDurationIntoMinutesAndSeconds, fromMeters } from "../../utils/HelperFunctions"
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";



export default function SetsRepsDuration ({exercise, activeWorkout}) {
   const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
   const [workoutExerciseDetail, setWorkoutExerciseDetail] = useState(null);
    
   const getWorkoutExerciseDetail = () => {
        const wed = activeWorkout.find(detail => {
            return detail.exerciseId === exercise.exerciseId});
        setWorkoutExerciseDetail(wed);
    }

    const determineSetsRepsOrDuration = (exercise) => {
        console.log('workoutexercisedetail is', workoutExerciseDetail)
        // console.log('exercise is', exercise)
        let exerciseInfo;
        if (exercise.exerciseType === 'CARDIO') {
            console.log("exercise is cardio, exercise duration is", workoutExerciseDetail.sets[0].duration);
            exerciseInfo = formatExerciseDurationIntoMinutesAndSeconds(workoutExerciseDetail.sets[0].duration);
            if(exercise.name==="swim") {
                exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, "yd")}`
            }
            else{
                exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, "mi")}`
            }

        } else {
            if (workoutExerciseDetail) {
                exerciseInfo = displayRepSets(workoutExerciseDetail);
            }
        }
        return <Typography align="center" className="activeWorkout_exerciseDetails">
        {exerciseInfo}
    </Typography>;
    }

    const displayRepSets = (workoutExerciseDetail) => {
        const sets = workoutExerciseDetail.sets;
        if (!sets || sets.length === 0) return "No data";
    
        let combinedSets = [];
        let count = 1;
    
        for (let i = 1; i <= sets.length; i++) {
            const current = sets[i];
            const prev = sets[i - 1];
    
            // If current exists and is equal to previous, just increment the count
            if (current && current.reps === prev.reps && current.weight === prev.weight) {
                count++;
            } else {
                // Save the last grouping
                combinedSets.push(`${count}x${prev.reps}:${prev.weight} lbs`);
                count = 1; // Reset counter
            }
        }
    
        return combinedSets.join("\n");
    };

    const handleEditClick = () => {
        // setEditingExercise(exercise);
        setOpenEditExerciseModal(true);
        console.log('you clicked edit on sets and reps')
        // would need to pass these in from ActiveWorkoutPage as well. 
        // then the "openEditExerciseModal" value would need to be passed to the EditExerciseModal
    };

    useEffect(() => {
        getWorkoutExerciseDetail()
    }, [])
    
    return (
        <Box sx={{position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            {determineSetsRepsOrDuration(exercise)}
            <IconButton onClick={() => handleEditClick()}>
                <Edit />
            </IconButton>
            <EditExerciseModal openEditExerciseModal={openEditExerciseModal} setOpenEditExerciseModal={setOpenEditExerciseModal} exercise={exercise} workoutExerciseDetail={workoutExerciseDetail}/>
        </Box>
    )
}