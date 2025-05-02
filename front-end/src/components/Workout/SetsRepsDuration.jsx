import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters } from "../../utils/HelperFunctions";
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";
import { useMemo } from "react";

export default function SetsRepsDuration({ exercise, activeWorkout, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
  // const [workoutExerciseDetail, setWorkoutExerciseDetail] = useState(null);
  const [hours, setHours] = useState(exercise?.duration?.hours || "");
  const [minutes, setMinutes] = useState(exercise?.duration?.minutes || "");
  const [seconds, setSeconds] = useState(exercise?.duration?.seconds || "");
  const [distanceUnit, setDistanceUnit] =useState(exercise?.distance?.distanceUnit || "")
  const [distance, setDistance] =useState(exercise?.distance || "")

  // const exerciseDuration = useMemo(() => {
  //   if (!workoutExerciseDetail) return null;
  //   const timeInSeconds = workoutExerciseDetail.sets[0].duration;
  //   return convertFromSeconds(timeInSeconds);
  // }, [workoutExerciseDetail]);

  // const getWorkoutExerciseDetail = () => {
  //   const wed = activeWorkout.find(detail => detail.exerciseId === exercise.exerciseId);
  //   setWorkoutExerciseDetail(wed);
  // };

  // const setTimes = () => {
  //   if (exerciseDuration) {
  //     return `H:${hours} M:${minutes} S:${seconds < 10 ? "0" + seconds : seconds}`;
  //   }
  //   return "";
  // };

  // const determineSetsRepsOrDuration = () => {
  //   if (!exercise) {
  //     return <Typography align="center" className="activeWorkout_exerciseDetails">Loading...</Typography>;
  //   }
  //   let exerciseInfo;
  //   if (exercise.exerciseType === 'CARDIO') {
  //     exerciseInfo = displayCardioSets();
  //   } else {
  //     exerciseInfo = displayRepSets(workoutExerciseDetail);
  //   }
  //   return <Typography align="center" className="activeWorkout_exerciseDetails">{exerciseInfo}</Typography>;
  // };

  const displayCardioSets = () => {
    let displayDistance = ""
    let displayDuration = ""
    if (hours || minutes || seconds) {
      displayDistance = `H:${hours} M:${minutes} S:${seconds < 10 ? "0" + seconds : seconds}`
    }
    if (distance) {
      displayDuration = `${distance} ${distanceUnit}`;
    }
    console.log("1) in display cardio sets, displaydistance is", displayDistance, "displayduration is", displayDuration)  
    return (<>
    <Typography align="center" className="activeWorkout_exerciseDetails">{displayDistance}</Typography>
    <Typography align="center" className="activeWorkout_exerciseDetails">{displayDuration}</Typography>
    </>)
  }

  const displayRepSets = () => {
    const sets = exercise.sets;
    if (!sets || sets.length === 0) return "No data";
    
    let combinedSets = [];
    let count = 1;
    let displayReps=""

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
    
    if(combinedSets.length >1) {
      displayReps= combinedSets.join(",\n");
    } else {displayReps= combinedSets.join("\n");}

    return <Typography align="center" className="activeWorkout_exerciseDetails">{displayReps}</Typography>;
  };

  const handleEditClick = () => {
    setOpenEditExerciseModal(true);
    // console.log('You clicked edit on sets and reps');
  };

  // useEffect(() => {
  //   if (exercise && activeWorkout) {
  //     getWorkoutExerciseDetail();
  //   }
  // }, [exercise, activeWorkout]); 

  // useEffect(() => {
  //   if (exerciseDuration) {
  //     const { hours, minutes, seconds } = exerciseDuration;
  //     setHours(hours);
  //     setMinutes(minutes);
  //     setSeconds(seconds);
  //   }
  // }, [exerciseDuration]);

  return (
    <Box sx={{ position: "absolute", bottom: 4, right: 0, left: 0, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      {exercise.exerciseType === 'CARDIO' ? displayCardioSets() : displayRepSets()}
      <IconButton onClick={handleEditClick}>
        <Edit />
      </IconButton>
      {/* Passing workoutExerciseDetail as a prop */}
      <EditExerciseModal
        openEditExerciseModal={openEditExerciseModal}
        setOpenEditExerciseModal={setOpenEditExerciseModal}
        exercise={exercise}
        updateActiveWorkoutWithNewStats={updateActiveWorkoutWithNewStats}
        hours={hours} 
        minutes={minutes} 
        seconds={seconds} 
        setHours={setHours} 
        setMinutes={setMinutes} 
        setSeconds={setSeconds}
        distance={distance}
        setDistance={setDistance}
        setDistanceUnit={setDistanceUnit}
        distanceUnit = {distanceUnit}
      />
    </Box>
  );
}
