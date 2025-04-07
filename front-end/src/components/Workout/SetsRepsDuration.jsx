export default function SetsRepsDuration (exercise, workoutDetails) {
    // workoutDetails will be an array of objects
    // I might have to set workoutId on the backend since I don't have that yet.
    // [{"exerciseId": 1, "workoutId": 1, "sets": 4, "reps": 10, "weight": 45},
    // {"exerciseId": 2, "workoutId": 1, "duration": 20}]
    
    const determineWorkoutExerciseDetail = () => {
        const workoutExerciseDetail = workoutDetails.find(detail => detail.exerciseId === exercise.id);
        return workoutExerciseDetail
    }

    const determineSetsRepsOrDuration = (exercise) => {
        const workoutExerciseDetail = determineWorkoutExerciseDetail();
        let exerciseInfo = "4x10:_lbs";
        if (exercise.type === 'cardio') {
            exerciseInfo = workoutExerciseDetail ? `${workoutExerciseDetail.duration} minutes` : "15 minutes";
        } else {
            if (workoutExerciseDetail) {
                //"Extract sets, reps, and weight from workoutDetail, but if any of them are missing or undefined, assign them a default value (4, 10, and "_lbs" respectively)."
            const { sets = 4, reps = 10, weight = "_" } = workoutExerciseDetail; 
            exerciseInfo = `${sets}x${reps}:${weight} lbs`;
            }
        }
        return exerciseInfo;
    }

    const handleEditClick = (exercise) => {
        setEditingExercise(exercise);
        setOpenEditExerciseModal(true);
        // would need to pass these in from ActiveWorkoutPage as well. 
        // then the "openEditExerciseModal" value would need to be passed to the EditExerciseModal
    };


    
    return (
        <Box sx={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
            <Typography align="center" className="activeWorkout_exerciseDetails">
                {determineSetsRepsOrDuration(exercise)}
            </Typography>
            <IconButton onClick={() => handleEditClick(exercise.id)}>
                <Edit />
            </IconButton>
        </Box>
    )
}