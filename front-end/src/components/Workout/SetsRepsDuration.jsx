import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { formatExerciseDurationIntoMinutesAndSeconds, fromMeters } from "../../utils/HelperFunctions";
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";

export default function SetsRepsDuration({ exercise, activeWorkout, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
  const [workoutExerciseDetail, setWorkoutExerciseDetail] = useState(null);

  // Fetch workout exercise detail based on the exerciseId
  const getWorkoutExerciseDetail = () => {
    const wed = activeWorkout.find(detail => detail.exerciseId === exercise.exerciseId);
    setWorkoutExerciseDetail(wed);
  };

  // UseEffect to call getWorkoutExerciseDetail once the component is mounted
  useEffect(() => {
    if (exercise && activeWorkout) {
      getWorkoutExerciseDetail();
    }
  }, [exercise, activeWorkout]); // Re-run when either exercise or activeWorkout changes

  const determineSetsRepsOrDuration = () => {
    if (!workoutExerciseDetail) {
      return <Typography align="center" className="activeWorkout_exerciseDetails">Loading...</Typography>;
    }

    let exerciseInfo;
    if (exercise.exerciseType === 'CARDIO') {
      exerciseInfo = formatExerciseDurationIntoMinutesAndSeconds(workoutExerciseDetail.sets[0].duration);
      if (exercise.name === "swim") {
        exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, "yd")}`;
      } else {
        exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, "mi")} mile`;
      }
    } else {
      exerciseInfo = displayRepSets(workoutExerciseDetail);
    }

    return <Typography align="center" className="activeWorkout_exerciseDetails">{exerciseInfo}</Typography>;
  };

  const displayRepSets = (workoutExerciseDetail) => {
    const sets = workoutExerciseDetail.sets;
    if (!sets || sets.length === 0) return "No data";

    let combinedSets = [];
    let count = 1;

    for (let i = 1; i <= sets.length; i++) {
      const current = sets[i];
      const prev = sets[i - 1];

      if (current && prev && current.reps === prev.reps && current.weight === prev.weight) {
        count++;
      } else {
        combinedSets.push(`${count}x${prev.reps}:${prev.weight} lbs`);
        count = 1;
      }
    }

    return combinedSets.join("\n");
  };

  const handleEditClick = () => {
    setOpenEditExerciseModal(true);
    // console.log('You clicked edit on sets and reps');
  };

  return (
    <Box sx={{ position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      {determineSetsRepsOrDuration()}
      <IconButton onClick={handleEditClick}>
        <Edit />
      </IconButton>
      {/* Passing workoutExerciseDetail as a prop */}
      <EditExerciseModal
        openEditExerciseModal={openEditExerciseModal}
        setOpenEditExerciseModal={setOpenEditExerciseModal}
        exercise={exercise}
        workoutExerciseDetail={workoutExerciseDetail}
        updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}
      />
    </Box>
  );
}
