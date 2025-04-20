import * as React from 'react';
import { Button, Box, Typography, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useState } from "react";
import CardioSet from './CardioSet';
import RepSet from './RepSet';

export default function EditExerciseModal({ openEditExerciseModal, setOpenEditExerciseModal, exercise, workoutExerciseDetail, updateActiveWorkoutWithNewStats }) {

    const [sets, setSets] = useState([1]);
    const [allReps, setAllReps] = useState([""]);
    const [allWeights, setAllWeights] = useState([""]);
    const [distance, setDistance] = useState(workoutExerciseDetail?.sets?.[0]?.distance || "");
    const [duration, setDuration] = useState(workoutExerciseDetail?.sets?.[0]?.duration || "");
    const exerciseType = exercise.exerciseType;

    console.log('workoutxercisedetail is', workoutExerciseDetail)
    console.log('distance is', distance, 'duration is', duration)

    const handleClose = () => {
        setOpenEditExerciseModal(false);
    };

    const handleSaveCardioEdits = () => {
        const newSets = {"distance": distance, "duration": duration}
        let updatedExerciseDetail = workoutExerciseDetail
        updatedExerciseDetail["sets"] = newSets;
        return updatedExerciseDetail;
    }

    const handleSaveEdits = () => {
        let updatedExerciseDetail;
        if (exerciseType === "CARDIO") {
            updatedExerciseDetail = handleSaveCardioEdits()
        }
        else {

            const newSets = [];
            for(let i=0; i<allReps.length; i++) {
                newSets[i] = {"reps": allReps[i], "weight": allWeights[i]}
            }
            // console.log("newsets are", newSets)
            // console.log('****workoutex detail', workoutExerciseDetail)
            updatedExerciseDetail = workoutExerciseDetail
            updatedExerciseDetail["sets"] = newSets;
            // console.log('updatedexdetails are', updatedExerciseDetail)
            // console.log('on close, sets are', sets, 'allReps are', allReps, 'allWeights are', allWeights)
        }
        updateActiveWorkoutWithNewStats(updatedExerciseDetail)
        setOpenEditExerciseModal(false);
    };

    

    const addSet = () => {
        setSets([...sets, sets.length + 1]); 
        setAllReps((prev) => [...prev, allReps[allReps.length - 1] || ""]);
        setAllWeights((prev) => [...prev, allWeights[allWeights.length - 1] || ""]);
        // duplicates the previous value into the new set value
    };

    return (
        <>
            <Dialog
                open={openEditExerciseModal}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                sx={{ width: '90%', maxWidth: 400 }}
            >
                <DialogTitle id="alert-dialog-title" sx={{fontSize: 17}}>
                    {`Edit ${exercise.name} Stats`}
                </DialogTitle>
                <Box sx={{ width: '90%', maxWidth: 400, display: 'flex', flexDirection: "column", justifyContent: "center", alignItems: "center" }}>
                    {exercise.exerciseType !== "CARDIO" ?
                        <>
                            {sets.map((setCount) => (
                                <RepSet key={setCount} setCount={setCount} allReps={allReps} allWeights={allWeights} setAllReps={setAllReps} setAllWeights={setAllWeights}/>
                            ))}
                            <AddIcon onClick={addSet} sx={{ cursor: 'pointer' }} />
                        </>
                        : <CardioSet distance={distance} duration = {duration} setDistance={setDistance} setDuration={setDuration} />
                    }
                </Box>
                <DialogActions>
                    <Button onClick={handleSaveEdits} autoFocus>
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}
