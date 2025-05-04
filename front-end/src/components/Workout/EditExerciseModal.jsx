import * as React from 'react';
import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useState, useEffect } from "react";
import CardioSet from './CardioSet';
import RepSet from './RepSet';
import { convertFromSeconds } from '../../utils/HelperFunctions';

export default function EditExerciseModal({ openEditExerciseModal, setOpenEditExerciseModal, exercise, updateActiveWorkoutWithNewStats, hours, minutes, seconds, setHours, setMinutes, setSeconds, distance, setDistance, setDistanceUnit, distanceUnit }) {

    const [sets, setSets] = useState([1]);
    const [allReps, setAllReps] = useState([""]);
    const [allWeights, setAllWeights] = useState([""]);
    const [inputDistance, setInputDistance] = useState(null);
    const [inputDistanceUnit, setInputDistanceUnit] = useState(null);
    const [inputDuration, setInputDuration] = useState(null);

    const exerciseType = exercise.exerciseType;

    // console.log('workoutxercisedetail is', exercise)
    // console.log('distance on edit modal is', distance, 'duration on edit modal is', duration)

    const handleClose = () => {
        setOpenEditExerciseModal(false);
    };

    const saveCardioEdits = () => {
        let updatedExerciseDetail = exercise
        const newSets = {"distance": inputDistance, "duration": inputDuration, "distanceUnit": inputDistanceUnit}
        console.log('3) in savecardio edits newsets are', newSets)
        updatedExerciseDetail["sets"] = [newSets];
        // You're modifying the object directly, which React doesn’t detect as a "state change."
        // instead, Use a new object to trigger the update properly which react will render as a state change:
        updateActiveWorkoutWithNewStats(updatedExerciseDetail)
        setOpenEditExerciseModal(false);
    }

    const saveRepEdits = () => {
        let updatedExerciseDetail = exercise
            const newSets = [];
            for(let i=0; i<allReps.length; i++) {
                newSets[i] = {"reps": allReps[i], "weight": allWeights[i]}
            }
            // console.log("newsets are", newSets)
            // console.log('****workoutex detail', exercise)
            updatedExerciseDetail["sets"] = newSets;
            // You're modifying the object directly, which React doesn’t detect as a "state change."
            // instead, Use a new object to trigger the update properly which react will render as a state change:

            // console.log('on close, sets are', sets, 'allReps are', allReps, 'allWeights are', allWeights)
        console.log('4) EEM updatedexdetails are', updatedExerciseDetail)
        updateActiveWorkoutWithNewStats(updatedExerciseDetail)
        setOpenEditExerciseModal(false);
    };

    

    const addSet = () => {
        setSets([...sets, sets.length + 1]); 
        setAllReps((prev) => [...prev, allReps[allReps.length - 1] || ""]);
        setAllWeights((prev) => [...prev, allWeights[allWeights.length - 1] || ""]);
        // duplicates the previous value into the new set value
    };

    // useEffect(() => {
    //     // console.log('*1 and 5, in use effect on EEM, workout ex dets are',exercise)
    //     if (exercise?.sets?.[0]) {
    //         setDistance(exercise.sets[0].distance || "");
    //         setDuration(exercise.sets[0].duration || "");
    //     }
    // }, [exercise]);

    return (
        <>
            <Dialog
                open={openEditExerciseModal}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                sx={{ width: '90%', maxWidth: 400, m:1 }}
                // possibly need to lower the margins
            >
                <DialogTitle id="alert-dialog-title" sx={{fontSize: 17}}>
                    {`Edit ${exercise.name} Stats`}
                </DialogTitle>
                {/* <Box sx={{ width: '100%', maxWidth: 400, display: 'flex', flexDirection: "column", justifyContent: "center", alignItems: "center" }}> */}
                    {exercise.exerciseType !== "CARDIO" ?
                        <>
                            {sets.map((setCount) => (
                                <RepSet key={setCount} setCount={setCount} allReps={allReps} allWeights={allWeights} setAllReps={setAllReps} setAllWeights={setAllWeights}/>
                            ))}
                            <AddIcon onClick={addSet} sx={{ cursor: 'pointer' }} />
                        </>
                        : <CardioSet distance={distance} setInputDistance={setInputDistance} setInputDuration={setInputDuration} hours={hours} minutes={minutes} seconds={seconds} setHours={setHours} setMinutes={setMinutes} setSeconds={setSeconds} setInputDistanceUnit={setInputDistanceUnit} distanceUnit={distanceUnit}/>
                    }
                {/* </Box> */}
                <DialogActions>
                    <Button onClick={() => (exerciseType === "CARDIO" ? saveCardioEdits() : saveRepEdits())} autoFocus>
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}
