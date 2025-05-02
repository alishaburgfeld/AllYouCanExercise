import { Box, Typography, IconButton } from "@mui/material";
import { Edit } from '@mui/icons-material';
import { convertFromSeconds, formatExerciseDurationIntoMinutesAndSeconds, fromMeters } from "../../utils/HelperFunctions";
import EditExerciseModal from "./EditExerciseModal";
import { useEffect, useState } from "react";
import { useMemo } from "react";

export default function SetsRepsDuration({ exercise, activeWorkout, updateActiveWorkoutWithNewStats }) {
  const [openEditExerciseModal, setOpenEditExerciseModal] = useState(false);
  const [workoutExerciseDetail, setWorkoutExerciseDetail] = useState(null);
  const [hours, setHours] = useState("");
  const [minutes, setMinutes] = useState("");
  const [seconds, setSeconds] = useState("");
  const [distanceUnit, setDistanceUnit] =useState("m")

  const exerciseDuration = useMemo(() => {
    if (!workoutExerciseDetail) return null;
    const timeInSeconds = workoutExerciseDetail.sets[0].duration;
    return convertFromSeconds(timeInSeconds);
  }, [workoutExerciseDetail]);

  const getWorkoutExerciseDetail = () => {
    const wed = activeWorkout.find(detail => detail.exerciseId === exercise.exerciseId);
    setWorkoutExerciseDetail(wed);
  };

  

  const setTimes = () => {
    if (exerciseDuration) {
      return `H:${hours} M:${minutes} S:${seconds < 10 ? "0" + seconds : seconds}`;
    }
    return "";
  };

  const determineSetsRepsOrDuration = () => {
    if (!workoutExerciseDetail) {
      return <Typography align="center" className="activeWorkout_exerciseDetails">Loading...</Typography>;
    }
    let exerciseInfo;
    if (exercise.exerciseType === 'CARDIO') {
      exerciseInfo = displayCardioSets();
    } else {
      exerciseInfo = displayRepSets(workoutExerciseDetail);
    }
    return <Typography align="center" className="activeWorkout_exerciseDetails">{exerciseInfo}</Typography>;
  };

  const displayCardioSets = () => {
    let exerciseInfo = setTimes(workoutExerciseDetail?.sets?.[0]?.duration);
    // if (exercise.name === "swim") {
      exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, distanceUnit)} ${distanceUnit}`;
    // } else {
    //   exerciseInfo += `\n ${fromMeters(workoutExerciseDetail.sets[0].distance, "mi")} mile`;
    // }
    console.log("1) in display cardio sets, exerciseInfo is", exerciseInfo)  
    return exerciseInfo;
  }

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

  useEffect(() => {
    if (exercise && activeWorkout) {
      getWorkoutExerciseDetail();
    }
  }, [exercise, activeWorkout]); 

  useEffect(() => {
    if (exerciseDuration) {
      const { hours, minutes, seconds } = exerciseDuration;
      setHours(hours);
      setMinutes(minutes);
      setSeconds(seconds);
    }
  }, [exerciseDuration]);

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
        hours={hours} 
        minutes={minutes} 
        seconds={seconds} 
        setHours={setHours} 
        setMinutes={setMinutes} 
        setSeconds={setSeconds}
        setDistanceUnit={setDistanceUnit}
        distanceUnit = {distanceUnit}
      />
    </Box>
  );
}
