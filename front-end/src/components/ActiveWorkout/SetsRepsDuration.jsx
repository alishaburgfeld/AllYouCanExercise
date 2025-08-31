import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters, displayCardioText, displayReps } from "../../utils/HelperFunctions";
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";
import { useMemo } from "react";

export default function SetsRepsDuration({ exercise, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
  // console.log("on setsrepdur", 'exercise is',exercise)

  

  const handleEditClick = () => {
    setOpenEditExerciseModal(true);
  };

  const displayCardioSets =() => {
    let cardioValues = displayCardioText(exercise);
    return (
      <Box sx={{ alignItems: "center" }}>
        <Typography
          align="center"
          className="activeWorkout_exerciseDetails"
        >
          {cardioValues["displayDistance"]}
        </Typography>
        <Typography
          align="center"
          className="activeWorkout_exerciseDetails"
        >
          {cardioValues["displayDuration"]}
        </Typography>
      </Box>
    );
  }

  const displayRepSets = () => {
    let combinedSets = displayReps(exercise);
    return (
      <Box sx={{ alignItems: "center" }}>
        {combinedSets.map((item, index) => {
          return (
            <Typography
              key={index}
              align="center"
              className="activeWorkout_exerciseDetails"
            >
              {item}
            </Typography>
          );
        })}
      </Box>
    );
  }

  return (
    <Box sx={{ position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
      {exercise.exerciseType === 'CARDIO' ? displayCardioSets() : displayRepSets()}
      <IconButton onClick={handleEditClick}>
        <Edit />
      </IconButton>
      <EditExerciseModal
        openEditExerciseModal={openEditExerciseModal}
        setOpenEditExerciseModal={setOpenEditExerciseModal}
        exercise={exercise}
        updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}
      />
    </Box>
  );
}
