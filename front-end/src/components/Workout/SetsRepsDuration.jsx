import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters } from "../../utils/HelperFunctions";
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";
import { useMemo } from "react";

export default function SetsRepsDuration({ exercise, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
  // console.log("on setsrepdur", 'exercise is',exercise)

  const displayCardioSets = () => {
    const cardioSet = exercise?.sets?.[0];
    if (!cardioSet) return null;

    const { distance, distanceUnit, duration } = cardioSet;
    const { hours, minutes, seconds } = duration || {};

    let displayDistance = ""
    let displayDuration = ""
    if (hours || minutes || seconds) {
      displayDistance = `H:${hours} M:${minutes} S:${seconds < 10 ? "0" + seconds : seconds}`
    }
    if (distance) {
      displayDuration = `${distance} ${distanceUnit}`;
    }
    // console.log("1) in display cardio sets, displaydistance is", displayDistance, "displayduration is", displayDuration)  
    return (<Box sx={{alignItems: "center"}}>
    <Typography align="center" className="activeWorkout_exerciseDetails">{displayDistance}</Typography>
    <Typography align="center" className="activeWorkout_exerciseDetails">{displayDuration}</Typography>
    </Box>)
  }

  const displayRepSets = () => {
    const sets = exercise.sets;
    // console.log('in display rep sets, sets are', sets)
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
    console.log('combinedSets are', combinedSets)
    return (
      <Box sx={{alignItems: "center"}}>
        {combinedSets.map((item, index)=> {
          return <Typography key={index} align="center" className="activeWorkout_exerciseDetails">{item}</Typography>
        }
        )}
      </Box>)
  };

  const handleEditClick = () => {
    setOpenEditExerciseModal(true);
  };

  return (
    <Box sx={{ position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
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
