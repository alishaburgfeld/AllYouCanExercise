import { Box, Typography, IconButton} from "@mui/material";
import { Edit } from '@mui/icons-material';
import { formatExerciseDurationIntoMinutesAndSeconds } from "../../utils/HelperFunctions"
import EditExerciseModal from "./EditExerciseModal";
import { useState } from "react";



export default function SetsRepsDuration ({exercise, activeWorkout}) {
   const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
   
    const determineWorkoutExerciseDetail = () => {
        const workoutExerciseDetail = activeWorkout.find(detail => {
            return detail.exerciseId === exercise.id});
        return workoutExerciseDetail
    }

    const determineSetsRepsOrDuration = (exercise) => {
        const workoutExerciseDetail = determineWorkoutExerciseDetail();
        console.log('workoutexercisedetail is', workoutExerciseDetail)
        console.log('exercise is', exercise)
        let exerciseInfo;
        if (exercise.exerciseType === 'CARDIO') {
            console.log("exercise is cardio, exercise duration is", workoutExerciseDetail.duration);
            exerciseInfo = formatExerciseDurationIntoMinutesAndSeconds(workoutExerciseDetail.duration);
        } else {
            if (workoutExerciseDetail) {
                const { sets, reps, weight} = workoutExerciseDetail; 
                exerciseInfo = `${sets}x${reps}:${weight} lbs`;
            }
        }
        return <Typography align="center" className="activeWorkout_exerciseDetails">
        {exerciseInfo}
    </Typography>;
    }

    const handleEditClick = () => {
        // setEditingExercise(exercise);
        setOpenEditExerciseModal(true);
        console.log('you clicked edit on sets and reps')
        // would need to pass these in from ActiveWorkoutPage as well. 
        // then the "openEditExerciseModal" value would need to be passed to the EditExerciseModal
    };


    
    return (
        <Box sx={{position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            {determineSetsRepsOrDuration(exercise)}
            <IconButton onClick={() => handleEditClick()}>
                <Edit />
            </IconButton>
            <EditExerciseModal openEditExerciseModal={openEditExerciseModal} setOpenEditExerciseModal={setOpenEditExerciseModal} exercise={exercise}/>
        </Box>
    )
}