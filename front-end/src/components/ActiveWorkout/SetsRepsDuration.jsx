import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters, displayCardioText, displayReps } from "../../utils/HelperFunctions"
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";
import { useMemo } from "react";

export default function SetsRepsDuration({ exercise, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);

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
    console.log(" in display rep sets")
    let combinedSets = displayReps(exercise);
    const fontSize = getFontSize(combinedSets.length);

    return (
      <Box sx={{ alignItems: "center" }}>
        {combinedSets.map((item, index) => {
          return (
            <Typography
              key={index}
              align="center"
              sx={{ fontSize }}
              className="activeWorkout_exerciseDetails"
            >
              {item}
            </Typography>
          );
        })}
      </Box>
    );
  }

  const getFontSize = (length) => {
    if (length <= 2) return "1rem";
    if (length === 3) return "0.8rem";
    if (length >= 4) return "0.7rem";
  };

  console.log('in sets resps ex is', exercise)

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
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
